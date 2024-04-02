import org.junit.jupiter.api.Test

import java.io.File

class MarkovChainTest {

    @Test
    fun generateSentence() {
        val dune = File("texts/Frank Herbert - Dune.txt").readText()

        val markovChain = MarkovChain(dune, 6)

        println(markovChain.generateSentence())
        println(markovChain.generateSentence())
        println(markovChain.generateSentence())
        println(markovChain.generateSentence())
        println(markovChain.generateSentence())
    }
}