package dsa_day_6

import SearchableGraph
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class GraphTest {
    val graph = SearchableGraph<String>()

    @BeforeEach
    fun setUp() {
        graph.clear()

        graph.addVertex("Orlando")
        graph.addVertex("New York")
        graph.addVertex("Boston")
        graph.addVertex("Los Angeles")
        graph.addVertex("Hong Kong")
        graph.addVertex("Shen Zhen")

        graph.addEdge("Orlando", "New York")
        graph.addEdge("Orlando", "Boston")
        graph.addEdge("Boston", "New York")
        graph.addEdge("Hong Kong", "Shen Zhen")
        graph.addEdge("Los Angeles", "Hong Kong")
        graph.addEdge("Orlando", "Los Angeles")
    }

    @Test
    fun breadthFirstSearch() {
        assertTrue(bfs("Orlando", "Shen Zhen"))
        assertTrue(bfs("Orlando", "Boston"))
        assertFalse(bfs("Boston", "Shen Zhen"))
    }

    private fun bfs(from: String, to: String): Boolean {
        return graph.breadthFirstSearch(from, to)
    }

    @Test
    fun depthFirstSearch() {
        assertTrue(dfs("Orlando", "Shen Zhen"))
        assertTrue(dfs("Orlando", "Boston"))
        assertFalse(dfs("Boston", "Shen Zhen"))
    }

    private fun dfs(from: String, to: String): Boolean {
        return graph.depthFirstSearch(from, to)
    }
}