interface Stack<T> {
    /**
     * Add [data] to the top of the stack
     */
    fun push(data: T)
    /**
     * Remove the element at the top of the stack.  If the stack is empty, it remains unchanged.
     * @return the value at the top of the stack or nil if none exists
     */
    fun pop(): T?
    /**
     * @return the value on the top of the stack or nil if none exists
     */
    fun peek(): T?
    /**
     * @return true if the stack is empty and false otherwise
     */
    fun isEmpty(): Boolean
}

/**
 * An implementation of a LIFO data structure called Stack
 * @param T type of data stored in the stack
 */
class LinkedListStack<T>: Stack<T> {
    private var head: LinkedListStackNode<T>? = null

    /**
     * Push [data] onto the Stack
     * @param data the new value to be put onto the stack
     */
    override fun push(data: T) {
        val newHead = LinkedListStackNode(data)
        newHead.next = head
        head = newHead
    }

    /**
     * Pop the top element off the stack
     * @return the top element if stack is not empty, else null
     */
    override fun pop(): T? {
        val currentHead = head
        head = currentHead?.next
        return currentHead?.data
    }

    /**
     * Peek the top element without popping it
     * @return the top element if stack is not empty, else null
     */
    override fun peek(): T? {
        return head?.data
    }

    /**
     * @return true if the stack is empty, else false
     */
    override fun isEmpty(): Boolean {
        return head == null
    }

}

private class LinkedListStackNode<T>(val data: T) {
    var next: LinkedListStackNode<T>? = null
}