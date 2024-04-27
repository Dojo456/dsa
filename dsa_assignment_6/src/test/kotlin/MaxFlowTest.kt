import org.example.FlowGraph
import org.example.MaxFlow
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests the MaxFlow class to ensure that it does indeed find maximum flow correctly of a flow graph
 */
class MaxFlowTest {
    @Test
    fun of() {
        // visualizations of below graphs can be found here:
        // https://www.geeksforgeeks.org/max-flow-problem-introduction/#
        var graph = FlowGraph(arrayOf(
            intArrayOf(0, 16, 13, 0, 0, 0),
            intArrayOf(0, 0, 10, 12, 0, 0),
            intArrayOf(0, 4, 0, 0, 14, 0),
            intArrayOf(0, 0, 9, 0, 0, 20),
            intArrayOf(0, 0, 0, 7, 0, 4),
            intArrayOf(0, 0, 0, 0, 0, 0)
        ), 6)

        assertEquals(23, MaxFlow.of(graph, 0, 5))

        graph = FlowGraph(arrayOf(
            intArrayOf(0, 3, 2, 0),
            intArrayOf(0, 0, 5, 2),
            intArrayOf(0, 0, 0, 3),
            intArrayOf(0, 0, 0, 0),
        ), 4)
        assertEquals(5, MaxFlow.of(graph, 0, 3))
    }
}