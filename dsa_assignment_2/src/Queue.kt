interface QueueInterface<T> {
    /**
     * Add [data] to the end of the queue.
     */
    fun enqueue(data: T)

    /**
     * Remove the element at the front of the queue.  If the queue is empty, it remains unchanged.
     * @return the value at the front of the queue or nil if none exists
     */
    fun dequeue(): T?

    /**
     * @return the value at the front of the queue or nil if none exists
     */
    fun peek(): T?

    /**
     * @return true if the queue is empty and false otherwise
     */
    fun isEmpty(): Boolean
}

class Queue<T> : QueueInterface<T> {
    private val backingList = LinkedList<T>()

    override fun enqueue(data: T) {
        return backingList.pushFront(data)
    }

    override fun dequeue(): T? {
        return backingList.popBack()
    }

    override fun peek(): T? {
        return backingList.peekBack()
    }

    override fun isEmpty(): Boolean {
        return backingList.isEmpty()
    }


}