import sorter.mergeSort
import sorter.selectionSort
import sorter.quickSort
import sorter.radixSort
import kotlin.math.abs
import kotlin.math.pow
import kotlin.random.Random
import kotlin.random.nextUInt
import kotlin.system.measureTimeMillis

// Everything after this is in red
val red = "\u001b[31m"
val blue = "\u001b[34m"

// Resets previous color codes
val reset = "\u001b[0m"

class Analyzer {
    private val measurements = mutableMapOf<String, MutableMap<Int, Int>>()

    private val sorts = mapOf<String, (List<Int>) -> List<Int>>(
        "Merge" to ::mergeSort,
        "Selection" to ::selectionSort,
        "Quick" to ::quickSort,
        "Radix" to ::radixSort
    )

    private fun measure(sorterName: String) {
        println("Now measuring $sorterName Sort")
        val alg = sorts.getValue(sorterName)

        val runs = mutableMapOf<Int, Int>()

        for (i in 1..23) {
            if (i > 16 && sorterName == "Selection") {
                continue
            }
            val size = 2.0.pow(i).toInt()

            println("Beginning run $red$i$reset of size $red$size$reset")
            val list = Array(size) {
                abs(Random.nextInt())
            }.toList()
            var sorted: List<Int>

            val averageRunTime = Array<Long>(5) {
                val runTime = measureTimeMillis {
                    sorted = alg(list)
                }
                if (sorted != list.sorted()) {
                    throw Exception("list was not sorted correctly")
                }
                runTime
            }.average().toInt()

            println("Run complete, took on average $averageRunTime milliseconds for a total of ${averageRunTime * 5} milliseconds.\n")

            runs[size] = averageRunTime
        }

        measurements[sorterName] = runs
    }

    fun measureAll(): Map<String, MutableMap<Int, Int>> {
        for (key in sorts.keys) {
            measure(key)
        }

        return measurements
    }
}
