package sorter

import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.pow

/**
 * mergeSort has a time complexity of O(NlogN)
 */
fun <T: Comparable<T>> mergeSort(list: List<T>): List<T> {
    if (list.size < 2) {
        return list
    }

    val center = list.size / 2

    val firstPart = LinkedList(mergeSort(list.subList(0, center)))
    val secondPart = LinkedList(mergeSort(list.subList(center, list.size)).toMutableList())
    val parts = arrayOf(firstPart, secondPart)

    val sorted = mutableListOf<T>()

    while (firstPart.isNotEmpty() || secondPart.isNotEmpty()) {
        val first = firstPart.firstOrNull()
        val second = secondPart.firstOrNull()

        // chosen is used to either take from the left half or right half of tree.
        // left is 0, right is 1
        val chosen: Int = if (first == null) {
            1
        } else if (second == null) {
            0
        } else if (first < second) {
            0
        } else {
            1
        }

        sorted.addLast(parts[chosen].removeFirst())
    }

    return sorted
}

/**
 * selectionSort has a time complexity of O(N^2)
 */
fun <T: Comparable<T>> selectionSort(list: List<T>): List<T> {
    val sorted = list.toMutableList()

    for (i in 0..<sorted.size) {
        var lowest = sorted[i]

        for (j in i..<sorted.size) {
            val elem = sorted[j]
            if (elem < lowest) { // swap
                sorted[j] = lowest
                lowest = elem
                sorted[i] = lowest
            }
        }
    }

    return sorted
}

/**
 * quickSort has a time complexity of O(N^2), however its average case is NlogN
 */
fun <T: Comparable<T>> quickSort(list: List<T>): List<T> {
    return recurseQuickSort(list.toMutableList())
}
private fun <T: Comparable<T>> recurseQuickSort(list: MutableList<T>): List<T> {
    if (list.size < 2) {
        return list
    }

    var store = 1

    for (i in 1..<list.size) {
        val elem = list[i]

        if (elem < list[0]) {
            list[i] = list[store]
            list[store] = elem
            store++
        }
    }

    val temp = list[store-1]
    list[store-1] = list[0]
    list[0] = temp

    recurseQuickSort(list.subList(0, store))
    recurseQuickSort(list.subList(store, list.size))

    return list
}

/**
 * radixSort has a time complexity of O(N)
 */
fun radixSort(list: List<Int>): List<Int> {
    val buckets = Array(10) { ArrayDeque<Int>() }
    val sorted = list.toMutableList()

    var power = 0

    while (true) {
        var greaterThanAll = true

        for (elem in sorted) {
            val divideBy = 10.0.pow(power).toInt()
            val truncated: Int = elem / divideBy

            val digit = truncated % 10

            buckets[digit].addFirst(elem)
            greaterThanAll = greaterThanAll && divideBy > elem
        }

        if (greaterThanAll) {
            break
        }

        sorted.clear()

        for (bucket in buckets) {
            while (!bucket.isEmpty()) {
                sorted.addLast(bucket.removeLast())
            }
        }

        power += 1
    }

    return sorted
}
