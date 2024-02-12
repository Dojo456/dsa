fun main() {
    val graph = SearchableGraph<String>()
    graph.addEdge("Orlando", "Boston", 4.0)
    graph.addEdge("Orlando", "ShenZhen", 11.0)
    graph.addEdge("Orlando", "DC", 1.0)

    graph.addEdge("DC", "LA", 4.0)

    graph.addEdge("Boston", "ShenZhen", 7.0)
    graph.addEdge("LA", "ShenZhen", 5.0)

    println(graph.getVertices())
    println(shortestPath(graph,"ShenZhen", "Orlando"))
}

/**
 * shortestPath finds the shortest path [from] to [to] using Dijkstra's algorithm.
 * @param from the vertex to start from
 * @param to the vertex to end at
 * @return a [Pair] contain the path of vertices, inclusively containing the start and end, and the total cost of the shortest path [from] to [to]. If a path does not exist, returns null.
 */
fun <VertexType> shortestPath(graph: SearchableGraph<VertexType>, from:VertexType, to: VertexType): Pair<List<VertexType>, Double>? {
    val vertices = graph.getVertices()

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

        for (neighbor in graph.getEdges(current)) {
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

    if (prev[to] == null) return null

    // now reconstruct path
    val path = mutableListOf(to)

    var traverseCurrent = to
    do {
        traverseCurrent = prev[traverseCurrent]!!
        path.add(traverseCurrent)
    } while (traverseCurrent != from)

    return Pair(path.toList().reversed(), dist[to]!!)
}