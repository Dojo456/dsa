class SearchableGraph<VertexType>: Graph<VertexType>() {
    fun breadthFirstSearch(from: VertexType, to: VertexType): Boolean {
        return generalSearch(from, to, Queue())
    }

    fun depthFirstSearch(from: VertexType, to: VertexType): Boolean {
        return generalSearch(from, to, Stack())
    }

    private abstract class PriorityList<VertexType> {
        protected var list = ArrayDeque<VertexType>(0)

        /**
         * Add a new element to the list, priority is set automatically.
         * @param element the element to be added.
         */
        abstract fun add(element: VertexType)

        /**
         * Gets the element of highest priority.
         * @return the element of highest priority.
         */
        abstract fun get(): VertexType?

        fun isEmpty(): Boolean {
            return list.isEmpty()
        }
    }

    private class Stack<VertexType> : PriorityList<VertexType>() {
        override fun add(element: VertexType) {
            list.addFirst(element)
        }

        override fun get(): VertexType? {
            return list.removeFirstOrNull()
        }
    }

    private class Queue<VertexType> : PriorityList<VertexType>() {
        override fun add(element: VertexType) {
            list.addFirst(element)
        }

        override fun get(): VertexType? {
            return list.removeLastOrNull()
        }

    }

    private fun generalSearch(from: VertexType, to: VertexType, priorityList: PriorityList<VertexType>): Boolean {
        val wilLVisit = mutableSetOf<VertexType>()

        wilLVisit.add(from)
        priorityList.add(from)

        while (!priorityList.isEmpty()) {
            val current = priorityList.get()

            if (current == to) {
                return true
            }

            edges[current]?.also { connected ->
                for (node in connected) {
                    if (!wilLVisit.contains(node)) {
                        priorityList.add(node)
                        wilLVisit.add(node)
                    }
                }
            }
        }

        return false
    }
}