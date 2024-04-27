package org.example

import java.security.InvalidParameterException
import kotlin.math.min

object MaxFlow {
    /**
     * Finds a path from the graph's source to sink using bread-first search.
     * @param rGraph the residual graph to be searched
     * @param source the source of the graph
     * @param sink the sink of the graph
     * @param parent an IntArray containing the parent of each node
     * @return True if path exists, False if no path
     */
    private fun bfs(rGraph: FlowGraph, source: Int, sink: Int, parent: IntArray): Boolean {
        val q = ArrayDeque<Int>()
        q.add(source)

        val toVisit = mutableSetOf(source)

        while (q.isNotEmpty()) {
            val current = q.removeFirst()

            for (i in 0..<rGraph.size) { // iterate through every node in the graph
                if (!toVisit.contains(i) && rGraph[current, i] > 0) { // if not about to visit and residual graph has capacity
                    q.add(i)
                    parent[i] = current
                    toVisit.add(i)
                }
            }
        }

        return toVisit.contains(sink)
    }

    /**
     * Finds the max flow of [graph] from [source] to [sink] using Ford-Fulkerson
     * @param graph the flow graph to be searched
     * @param source the source of flow
     * @param sink the sink of flow
     * @return the maximum flow possible
     */
    fun of(graph: FlowGraph, source: Int, sink: Int): Int {
        val rGraph = graph.clone()

        var maxFlow = 0
        val parent = IntArray(graph.size)

        while (bfs(rGraph, source, sink, parent)) { // while there are edges with remaining capacity from source to sink
            var pathFlow = Int.MAX_VALUE

            var current = sink
            while (current != source) { // find max flow of naive path
                val next = parent[current]
                pathFlow = min(pathFlow, rGraph[next, current])
                current = next
            }

            current = sink
            while (current != source) { // adjust residual graph
                val next = parent[current]

                rGraph[next, current] -= pathFlow
                rGraph[current, next] += pathFlow // notice how a reverse path is added
                // this adding of capacity to a reverse edge is what allows Ford-Fulkerson to essentially "undo" operations

                current = next
            }

            maxFlow += pathFlow
        }

        return maxFlow
    }
}

/**
 * @param nodes all nodes in the graph represented as an adjacency matrix
 * @param size the number of nodes in the graph, should be equal to the dimensions of [nodes]
 */
class FlowGraph(private val nodes: Array<IntArray>, val size: Int) {
    init {
        if (nodes.size != size) throw InvalidParameterException("dimensions must equal to size")

        for (row in nodes) {
            if (row.size != size) throw InvalidParameterException("dimensions must equal to size")
        }
    }

    operator fun get(i: Int, j: Int): Int {
        return nodes[i][j]
    }

    operator fun set(i: Int, j: Int, value: Int) {
        nodes[i][j] = value
    }

    fun clone(): FlowGraph {
        return FlowGraph(Array(size) {
            i -> nodes[i].clone()
        }, size)
    }
}