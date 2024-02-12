/**
 * Representation of a min heap
 * @param T the type of the heap elements
 * @property vertices these hold the tree structure using the scheme in
 *     https://en.wikipedia.org/wiki/Heap_(data_structure)
 * @property indexMap this is used for quick lookups of existing vertices
 */
class MinHeap<T> {
    private var vertices: MutableList<Pair<T, Double>> = mutableListOf()
    private var indexMap: MutableMap<T, Int> = mutableMapOf()

    /**
     * @return true if the heap is empty and false otherwise
     */
    fun isEmpty(): Boolean {
        return vertices.isEmpty()
    }

    /**
     * Insert [data] into the heap with value [heapNumber].
     * @param data the data to be inserted
     * @param heapNumber the priority of the data to be inserted. If omitted, it is automatically set to [Double.MAX_VALUE].
     * @return true if [data] is added and false if [data] was already there
     */
    fun insert(data: T, heapNumber: Double = Double.MAX_VALUE):Boolean {
        if (contains(data)) {
            return false
        }

        if (heapNumber.isInfinite()) {
            throw IllegalArgumentException("heapNumber must be finite")
        }

        vertices.add(Pair<T, Double>(data, heapNumber))
        indexMap[data] = vertices.size - 1
        percolateUp(vertices.size - 1)
        return true
    }

    /**
     * @return the minimum value in the heap (or null if heap is empty)
     */
    fun getMin(): T? {
        when (vertices.size) {
            0 -> {
                return null
            }
            1 -> {
                val tmp = vertices[0].first
                vertices = mutableListOf()
                return tmp
            }
            else -> {
                val tmp = vertices[0].first
                swap(0, vertices.size - 1)
                vertices.removeLast()
                indexMap.remove(tmp)
                bubbleDown(0)
                return tmp
            }
        }
    }

    /**
     * Change the number of an element
     * @param vertex the element to change
     * @param newNumber the new number for the element
     */
    fun adjustHeapNumber(vertex: T, newNumber: Double) {
        if (newNumber.isInfinite()) throw IllegalArgumentException("heapNumber must be finite")

        getIndex(of=vertex)?.also{ index ->
            vertices[index] = Pair(vertices[index].first, newNumber)
            // do both operations to avoid explicitly testing which way to go
            percolateUp(startIndex=index)
            bubbleDown(startIndex=index)
        }
    }

    /**
     * @return true if the element is in the heap, false otherwise
     */
    fun contains(vertex: T): Boolean {
        return getIndex(of=vertex) != null
    }

    /**
     * @return the index in the list where the element is stored (or null if
     *     not there)
     */
    private fun getIndex(of: T): Int? {
        return indexMap[of]
    }

    /**
     * Bubble down from [startIndex] if needed
     * @param startIndex the index in the tree to start the bubbling
     */
    private fun bubbleDown(startIndex: Int) {
        val startNumber = vertices[startIndex].second
        val leftIndex = getLeftIndex(of=startIndex)
        val rightIndex = getRightIndex(of=startIndex)
        val leftNumber = if (leftIndex >= vertices.size) Double.POSITIVE_INFINITY else vertices[leftIndex].second
        val rightNumber = if (rightIndex >= vertices.size) Double.POSITIVE_INFINITY else vertices[rightIndex].second
        if (startNumber < leftNumber && startNumber < rightNumber) {
            return
        } else if (leftNumber < rightNumber) {
            // swap with left since it is smallest
            swap(leftIndex, startIndex)
            bubbleDown(leftIndex)
            return
        } else {
            // swap with right since it is smallest
            swap(rightIndex, startIndex)
            bubbleDown(rightIndex)
            return
        }
    }

    /**
     * Swap [index1] and [index2] in the tree
     * @param index1 the first element to swap
     * @param index2 the second element to swap
     */
    private fun swap(index1: Int, index2: Int) {
        // update our index map so we still can find thigns
        indexMap[vertices[index1].first] = index2
        indexMap[vertices[index2].first] = index1
        val tmp = vertices[index1]
        vertices[index1] = vertices[index2]
        vertices[index2] = tmp
    }

    /**
     * Percolate up from [startIndex] if needed
     * @param startIndex the index in the tree to start the percolation
     */
    private fun percolateUp(startIndex: Int) {
        val parentIndex = getParentIndex(of = startIndex)
        if (parentIndex < 0) {
            // we must be at the root
            return
        } else if (vertices[startIndex].second < vertices[parentIndex].second) {
            swap(parentIndex, startIndex)
            percolateUp(parentIndex)
        }
    }

    /**
     * Get the parent index in the list
     * @param of the index to start from
     * @return the index where the parent is stored (if applicable)
     */
    private fun getParentIndex(of: Int):Int {
        return (of - 1) / 2
    }

    /**
     * Get the left index in the list
     * @param of the index to start from
     * @return the index where the left child is stored (if applicable)
     */
    private fun getLeftIndex(of: Int):Int {
        return of * 2 + 1
    }

    /**
     * Get the right index in the list
     * @param of the index to start from
     * @return the index where the right child is stored (if applicable)
     */
    private fun getRightIndex(of: Int):Int {
        return of * 2 + 2
    }
}