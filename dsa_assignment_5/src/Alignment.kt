data class Alignment(val seq1: String, val seq2: String, val score: Int) {
    override fun toString(): String {
        return """
            Score: $score
            Sequence 1: $seq1
            Sequence 2: $seq2
        """.trimIndent()
    }
}

// Global sequence alignment using Needleman-Wunsch
fun align(seq1: String, seq2: String): Alignment {
    // visualize sequence 1 as vertical and sequence 2 as horizontal
    val operableSeq1 = "-$seq1"
    val operableSeq2 = "-$seq2"

    val scores = Array(operableSeq1.length) {Array(operableSeq2.length) {0} }
    // with prev, prevX represents the change in X going to the previous. So a left arrow would be -1 in x and 0 in y.
    val prevX = Array(operableSeq1.length) {Array(operableSeq2.length) {0} }
    val prevY = Array(operableSeq1.length) {Array(operableSeq2.length) {0} }

    fun calcScore(i: Int, j: Int) {
        // the scores of all adjacent boxes
        val left = if (j > 0) scores[i][j - 1] else null
        val up = if (i > 0) scores[i-1][j] else null
        val diag = if (i > 0 && j > 0) scores[i-1][j-1] else if (i == 0 && j == 0) 0 else null

        // if matched, then diagonal should add 1 to its score
        val match = if (i == 0 && j == 0) 0 else if (operableSeq1[i] == operableSeq2[j]) 1 else -1
        // this weird null check thing is because for the edges of the table, adjacent cells are null
        val choices = listOf(left?.dec(), diag?.plus(match), up?.dec()).map { e -> e ?: Int.MIN_VALUE }

        val max = choices.max()
        val arrow = choices.indexOf(max)

        scores[i][j] = max
        prevX[i][j] = if (arrow == 2) 0 else -1
        prevY[i][j] = if (arrow == 0) 0 else -1
    }

    for (i in operableSeq1.indices) {
        for (j in i..<operableSeq2.length) {
            calcScore(i, j)
            calcScore(j, i)
        }
    }

    // table has been built, print to verify
//    println("scores")
//    println(Matrix(scores, seq2.split(""), seq2.split("")).toString())
//
//    // 2 is vertical arrow. -1 is horizontal arrow. 1 is diagonal.
//    println("arrows")
//    println((Matrix(prevX) + (Matrix(prevY) * -2)).toString())

    var i = operableSeq1.length - 1
    var j = operableSeq2.length - 1

    val score = scores[i][j]

    val rebuiltSeq1 = StringBuilder()
    val rebuiltSeq2 = StringBuilder()

    // rebuild sequence based on arrows
    while (i >= 0 && j >= 0) {
        val arrowX = prevX[i][j]
        val arrowY = prevY[i][j]

        rebuiltSeq2.append(if (arrowX == -1) operableSeq2[j] else "-")
        rebuiltSeq1.append(if (arrowY == -1) operableSeq1[i] else "-")

        i += arrowY
        j += arrowX
    }

    rebuiltSeq1.reverse()
    rebuiltSeq2.reverse()

    // if initial element of both alignments is gap, just truncate that
    if (rebuiltSeq1[0] == rebuiltSeq2[0] && rebuiltSeq1[0] == '-') {
        rebuiltSeq1.delete(0, 1)
        rebuiltSeq2.delete(0, 1)
    }

    return Alignment(rebuiltSeq1.toString(), rebuiltSeq2.toString(), score)
}