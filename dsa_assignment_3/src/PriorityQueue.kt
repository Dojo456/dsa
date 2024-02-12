class PriorityQueue<T>: MinPriorityQueue<T> {
    private val minHeap = MinHeap<T>()
    override fun isEmpty(): Boolean {
        return minHeap.isEmpty()
    }

    override fun next(): T? {
        return minHeap.getMin()
    }

    override fun adjustPriority(elem: T, newPriority: Double) {
        minHeap.adjustHeapNumber(elem, newPriority)
    }

    override fun addWithPriority(elem: T, priority: Double?) {
        priority?.run {
            minHeap.insert(elem, priority)
        } ?: {
            minHeap.insert(elem)
        }
    }
}