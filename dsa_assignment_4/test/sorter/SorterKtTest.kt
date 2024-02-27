package sorter

import org.junit.jupiter.api.Assertions.*

class SorterKtTest {
    private lateinit var list: List<Int>

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
        list = (0..9).toList().shuffled()
    }

    @org.junit.jupiter.api.Test
    fun mergeSort() {
        val sorted = mergeSort(list)
        assertEquals(sorted.sorted(), sorted)
    }

    @org.junit.jupiter.api.Test
    fun selectionSort() {
        val sorted = selectionSort(list)
        assertEquals(sorted.sorted(), sorted)
    }

    @org.junit.jupiter.api.Test
    fun quickSort() {
        val specificOrder = quickSort(listOf(5,7,9,8))
        assertEquals(specificOrder.sorted(), specificOrder)

        val sorted = quickSort(list)
        assertEquals(sorted.sorted(), sorted)
    }

    @org.junit.jupiter.api.Test
    fun radixSort() {
        val specificOrder = radixSort(listOf(5,7,9,8))
        assertEquals(specificOrder.sorted(), specificOrder)

        val sorted = radixSort(list)
        assertEquals(sorted.sorted(), sorted)

        val unsorted = (0..101).toList().shuffled()
        assertEquals(unsorted.sorted(), radixSort(unsorted))
    }
}