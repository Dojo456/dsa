/**
 * [SearchableGraph] is an implementation of a map data structure that has built in search functionality. It accepts a generic type [VertexType].
 */
class SearchableGraph<VertexType>: Graph<VertexType> {
    private val vertices = mutableSetOf<VertexType>()
    private val edges = mutableMapOf<VertexType, MutableMap<VertexType, Double>>()

    override fun getVertices(): Set<VertexType> {
        return vertices
    }

    override fun clear() {
        vertices.clear()
        edges.clear()
    }

    /**
     * resolveVertex should when a new vertex needs to be added. It adds a vertex and performs all necessary initializations for it to be used safely.
     * @param vertex the vertex that should be resolved
     */
    private fun resolveVertex(vertex: VertexType) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex)
            edges[vertex] = mutableMapOf()
        }
    }

    override fun getEdges(from: VertexType): Map<VertexType, Double> {
        // resolveVertex ensures the value is not null
        resolveVertex(from)

        return edges[from]!!.toMap()
    }

    override fun addEdge(from: VertexType, to: VertexType, cost: Double) {
        resolveVertex(from)
        resolveVertex(to)

        edges[from]!![to] = cost
    }

    /**
     * shortestPath finds the shortest path [from] to [to] using Dijkstra's algorithm.
     * @param from the vertex to start from
     * @param to the vertex to end at
     * @return a [Pair] contain the path of vertices, inclusively containing the start and end, and the total cost of the shortest path [from] to [to].
     */
    fun shortestPath(from: VertexType, to: VertexType): Pair<List<VertexType>, Double> {
        if (!vertices.contains(from)) throw IllegalArgumentException("$from does not exist in graph")

        if (!vertices.contains(to)) throw IllegalArgumentException("$to does not exist in graph")

        val visited = mutableSetOf<VertexType>()
        val queue = PriorityQueue<VertexType>()
        val prev = mutableMapOf<VertexType, VertexType>()
        val dist = mutableMapOf<VertexType, Double>()

        // initialize all variables to sensible defaults
        for (vertex in vertices) {
            if (vertex != from) {
                queue.addWithPriority(vertex, Double.MAX_VALUE)
                dist[vertex] = Double.POSITIVE_INFINITY
            }
        }

        // need to start at from
        dist[from] = 0.0
        queue.addWithPriority(from, 0.0)

        var current = queue.next()

        // effectively means while queue is not empty
        while(current != null) {
            visited.add(current)

            for (neighbor in edges[current] ?: emptyMap()) { // emptyMap used for null safety
                val vertex = neighbor.key
                val cost = neighbor.value
                if (!visited.contains(vertex)) { // have not yet visited this neighbor
                    // distance to this vertex from the current one we are on
                    val altDistance = dist[current]!! + cost
                    if (altDistance < dist[vertex]!!) {
                        dist[vertex] = altDistance
                        queue.adjustPriority(vertex, altDistance)
                        prev[vertex] = current
                    }
                }
            }

            current = queue.next()
        }

        // now reconstruct path
        val path = mutableListOf(to)

        var traverseCurrent = to
        do {
            traverseCurrent = prev[traverseCurrent]!!
            path.add(traverseCurrent)
        } while (traverseCurrent != from)

        return Pair(path.toList().reversed(), dist[to]!!)
    }
}