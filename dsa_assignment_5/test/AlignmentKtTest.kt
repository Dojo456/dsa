import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class AlignmentKtTest {

    @Test
    fun align() {
        val seq2 = "GCATGCG"
        val seq1 = "GATTACA"

        println(align(seq1, seq2))
    }
}