package sorter

import kotlin.math.pow

fun <T: Comparable<T>> mergeSort(list: List<T>): List<T> {
    if (list.size < 2) {
        return list
    }

    val center = list.size / 2

    val firstPart = ArrayDeque(mergeSort(list.subList(0, center)))
    val secondPart = ArrayDeque(mergeSort(list.subList(center, list.size)))
    val parts = arrayOf(firstPart, secondPart)

    val sorted = mutableListOf<T>()

    while (!firstPart.isEmpty() || !secondPart.isEmpty()) {
        val first = firstPart.firstOrNull()
        val second = secondPart.firstOrNull()

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

fun radixSort(list: List<Int>): List<Int> {
    val buckets = Array(10) { ArrayDeque<Int>() }
    val sorted = list.toMutableList()

    var done = false
    var power = 0

    while (!done) {
        done = true

        for (elem in sorted) {
            val truncated: Int = (elem.toDouble() / 10.0.pow(power)).toInt()

            if (truncated == 0) {
                continue
            }

            val digit = truncated % 10

            buckets[digit].addFirst(elem)
            done = false
        }

        sorted.clear()

        for (bucket in buckets) {
            while (!bucket.isEmpty()) {
                sorted.addFirst(bucket.removeLast())
            }
        }

        power += 1
    }

    return sorted
}
