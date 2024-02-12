/**
 * [SearchableGraph] is an implementation of a map data structure that can be used with search functions.
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
}