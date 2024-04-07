import org.junit.jupiter.api.Test
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Builds a Markov Chain based on Dune and output 5 sentences based on it.
 */
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