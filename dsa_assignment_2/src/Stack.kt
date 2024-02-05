interface StackInterface<T> {
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

class Stack<T> : StackInterface<T> {
    private var backingList = LinkedList<T>()

    override fun push(data: T) {
        backingList.pushFront(data)
    }

    override fun pop(): T? {
        return backingList.popFront()
    }

    override fun peek(): T? {
        return backingList.peekFront()
    }

    override fun isEmpty(): Boolean {
        return backingList.isEmpty()
    }

}