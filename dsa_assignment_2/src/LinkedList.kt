interface LinkedListInterface<T> {
    /**
     * Adds the element [data] to the front of the linked list.
     */
    fun pushFront(data: T)

    /**
     * Adds the element [data] to the back of the linked list.
     */
    fun pushBack(data: T)

    /**
     * Removes an element from the front of the list. If the list is empty, it is unchanged.
     * @return the value at the front of the list or nil if none exists
     */
    fun popFront(): T?

    /**
     * Removes an element from the back of the list. If the list is empty, it is unchanged.
     * @return the value at the back of the list or nil if none exists
     */
    fun popBack(): T?

    /**
     * @return the value at the front of the list or nil if none exists
     */
    fun peekFront(): T?

    /**
     * @return the value at the back of the list or nil if none exists
     */
    fun peekBack(): T?

    /**
     * @return true if the list is empty and false otherwise
     */
    fun isEmpty(): Boolean
}

/**
 * Generic doubly linked list that stores values of type [T]
 */
class LinkedList<T> : LinkedListInterface<T> {
    /**
     * Internal private class that will be used as the nodes of a linked list.
     */
    private class LinkedListNode<T>(
        val data: T,
        var next: LinkedListNode<T>? = null,
        var previous: LinkedListNode<T>? = null
    )

    /**
     * Number of elements in the list/
     */
    var length = 0
        private set

    private var head: LinkedListNode<T>? = null
    private var tail: LinkedListNode<T>? = null

    override fun pushFront(data: T) {
        val node = LinkedListNode(data)
        node.next = head
        head?.previous = node
        head = node

        // need null checks for special case that both head and tail are empty
        // these checks allow the two ends to be in sync and will point to the same node
        if (tail == null) {
            tail = head
        }
        length += 1
    }

    override fun pushBack(data: T) {
        val node = LinkedListNode(data)
        node.previous = tail
        tail?.next = node
        tail = node
        if (head == null) {
            head = tail
        }
        length += 1
    }

    override fun popFront(): T? {
        val data = head?.data
        // address special case of head and tail are same nodes
        // in this case, set both to null as opposed to just one
        if (tail == head) {
            tail = null
        }
        head = head?.next
        length -= 1
        return data
    }

    override fun popBack(): T? {
        val data = tail?.data
        if (head == tail) {
            head = null
        }
        tail = tail?.previous
        length -= 1
        return data
    }

    override fun peekFront(): T? {
        return head?.data
    }

    override fun peekBack(): T? {
        return tail?.data
    }

    override fun isEmpty(): Boolean {
        return head == null
    }

}