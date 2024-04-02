import kotlin.random.Random

class MarkovChain(val text: String, private val n: Int = 2) {
    private val probabilities = mutableMapOf<String, MutableMap<String, Float>>()
    private val startNGrams = mutableSetOf<String>()
    init {
        val counts = mutableMapOf<String, MutableMap<String, Int>>()
        // split string into words, and ensure each word is actually valid and contains at least one letter
        val tokens = text.replace(Regex("[\r\n]"), " ").split(Regex("[ \n\r]")).filter {
            it.contains(Regex("[a-zA-Z]"))
        }

        for (i in (0..<tokens.size - n)) { // build counts and startNGrams
            val ngram = tokens.subList(i, i + n).joinToString(" ")
            val next = tokens[i + n]

            // add count of next word to current state
            val wordCount = counts[ngram] ?: let {
                val newMap = mutableMapOf<String, Int>()
                counts[ngram] = newMap
                newMap
            }

            wordCount[next]?.inc() ?: run {
                wordCount[next] = 1
            }

            // check if ngram is a never seen before start ngram, if so, add
            val prevEnd = tokens.getOrNull(i - 1)?.let {
                it[it.length - 1].toString()
            } ?: ""
            if (i == 0 || (!startNGrams.contains(ngram) && prevEnd.matches(Regex("[?.!]")))) {
                startNGrams.add(ngram)
            }
        }

        // need to convert counts to normalized probabilities
        for ((ngram, nGramCount) in counts) {
            val sum = nGramCount.values.sum().toFloat()
            val normalized = mutableMapOf<String, Float>()

            for ((word, count) in nGramCount) {
                normalized[word] = count / sum
            }

            probabilities[ngram] = normalized
        }
    }

    fun generateSentence(): String {
        var sentence = startNGrams.random() // start sentence randomly

        while (!sentence.matches(Regex(".*[.?!]$"))) {
            sentence += " " + pickNextWord(sentence)
        }

        return sentence
    }

    private fun pickNextWord(sentence: String): String {
        val tokens = sentence.split(" ")

        val ngram = tokens.subList(tokens.size - n, tokens.size).joinToString(" ")

        var threshold = 0.0
        val r = Random.nextDouble()

        probabilities[ngram]?.forEach { (key, value) ->
            threshold += value

            if (r < threshold) {
                return key
            }
        }

        return "." // if never seen before ngram, just end sentence
    }
}