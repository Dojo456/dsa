fun findMaxSequence(list: List<Int>): Array<Int> {
    if (list.size < 2) {
        return arrayOf(0, 0, list.getOrNull(0) ?: 0)
    }

    val mid = list.size / 2
    val leftMax = findMaxSequence(list.subList(0, mid))
    val rightMax = findMaxSequence(list.subList(mid, list.size))

    val parts = arrayOf(leftMax, rightMax)

    parts[1].forEachIndexed { index, _ -> parts[1][index] += mid}

    val chosen: Int
    val other: Int
    val step: Int

    if (leftMax[2] > rightMax[2]) {
        chosen = 0
        other = 1
        step = 1
    } else {
        chosen = 1
        other = 0
        step = -1
    }

    var accumulated = 0

    var i = parts[chosen][other]
    while(i + step != parts[other][chosen]) {
        val elem = list[i]

        if (elem >= 0) { // if positive element, always include
            parts[chosen][other] += step
        } else {
            accumulated += elem

            if (parts[other][2] - accumulated < 0) { // incur negative cost if merge
                break
            }
        }

        i += step
    }

    return parts[chosen]
}

fun bruteFindMaxSequence(list: List<Int>): Array<Int> {
    var start = 0
    var end = 0
    var max = Int.MIN_VALUE

    for (i in list.indices) {
        for (j in i..<list.size) {
            val value = list.subList(i, j+1).sum()

            if (value > max) {
                start = i
                end = j
                max = value
            }
        }
    }

    return arrayOf(start, end, list.subList(start, end).sum())
}