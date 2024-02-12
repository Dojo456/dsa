import org.junit.jupiter.api.Assertions.*

class SearchableGraphTest {
    private val graph = SearchableGraph<String>()
    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
        graph.clear()
    }

    @org.junit.jupiter.api.Test
    fun getVertices() {
        assertTrue(graph.getVertices().isEmpty())
        val vertices = setOf("Orlando", "Boston", "LA", "ShenZhen")
        for (vertex in vertices) {
            graph.addEdge(vertex, vertex, 0.0)
        }
        assertEquals(vertices, graph.getVertices())
    }

    @org.junit.jupiter.api.Test
    fun getEdges() {
        graph.addEdge("Orlando", "Boston", 2.0)
        graph.addEdge("Orlando", "ShenZhen", 7.0)
        graph.addEdge("Orlando", "LA", 3.0)

        graph.addEdge("Boston", "ShenZhen", 4.0)
        graph.addEdge("LA", "ShenZhen", 2.9)
        assertEquals(graph.getEdges("Orlando"), mapOf(
            "Boston" to 2.0,
            "ShenZhen" to 7.0,
            "LA" to 3.0
        ))
    }

    @org.junit.jupiter.api.Test
    fun addEdge() {
        graph.addEdge("Orlando", "Boston", 2.0)
        graph.addEdge("Orlando", "ShenZhen", 7.0)
        graph.addEdge("Orlando", "LA", 3.0)

        graph.addEdge("Boston", "ShenZhen", 4.0)
        graph.addEdge("LA", "ShenZhen", 2.9)
        assertEquals(graph.getEdges("Orlando"), mapOf(
            "Boston" to 2.0,
            "ShenZhen" to 7.0,
            "LA" to 3.0
        ))
    }
}