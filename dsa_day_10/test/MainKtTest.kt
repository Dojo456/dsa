import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.random.Random

class MainKtTest {
    lateinit var list: List<Int>

    @BeforeEach
    fun setUp() {
        list = (0..5).toList().shuffled()
    }

    @Test
    fun findMaxSequence() {
        println("recursive")
        println(list)
        println(findMaxSequence(list).toList())
    }

    @Test
    fun bruteFindMaxSequence() {
        println("brute force")
        println(list)
        val seq = bruteFindMaxSequence(list)
        println(seq.toList())
    }
}