import kotlin.time.measureTime
import kotlin.math.ceil

fun main() {
    val arraySizes = listOf(100, 1000, 10000, 100000, 1000000, 10000000, 100000000)
    println("numberOfElements totalTime timePerElement")
    for (arraySize in arraySizes) {
        val myList = MyMutableIntList()
        val timeTaken = measureTime {
            for (i in 0..<arraySize) {
                myList.add(i)
            }
        }
        println("$arraySize $timeTaken ${timeTaken/arraySize}. ${myList.expansions} expansions done.")
    }
}


interface MyMutableListInterface {
    /**
     * Add [element] to the end of the list
     */
    fun add(element: Int)

    /**
     * Remove all elements from the list
     */
    fun clear()

    /*
     * @return the size of the list
     */
    fun size(): Int

    /**
     * @param index the index to return
     * @return the element at [index]
     */
    operator fun get(index: Int): Int

    /**
     * Store [value] at position [index]
     * @param index the index to set
     * @param value to store at [index]
     */
    operator fun set(index: Int, value: Int)
}

class MyMutableIntList: MyMutableListInterface {
    private var backingArray: IntArray = IntArray(0)
    var size = 0
        private set

    var expansions = 0

    override fun add(element: Int) {
        checkNeedMoreCap(1)
        backingArray[size] = element
        size++
    }

    private fun checkNeedMoreCap(delta: Int) {
        if (size + delta > backingArray.size) {
            expansions++
            val newSize = ceil(backingArray.size.toDouble()).toInt().coerceAtLeast(size + delta)
            backingArray = IntArray(newSize)
        }
    }

    override fun clear() {
        backingArray = IntArray(0)
        size = 0
    }

    override fun size(): Int {
        return size
    }

    override fun get(index: Int): Int {
        return backingArray[index]
    }

    override fun set(index: Int, value: Int) {
        backingArray[index] = value
    }

}

/**
 * PROBLEM 2:
 * I'd use a Kotlin data class, specifically a [Pair] to represent the edges and store all edges in a set.
 */