package org.example

import org.example.algorithm.Entity
import org.example.algorithm.Population
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.random.Random

fun main() {
    TravellingSalesperson.solve()
}

object TravellingSalesperson {
    val distances = arrayOf(
        intArrayOf(0, 12, 10, 19, 8),
        intArrayOf(12, 0, 3, 7, 2),
        intArrayOf(10, 3, 0, 6, 20),
        intArrayOf(19, 7, 6, 0, 4),
        intArrayOf(8, 2, 20, 4, 0),
    )

    fun generator(): String {
        return arrayOf('A', 'B', 'C', 'D', 'E').let {
            it.shuffle()
            it.joinToString("")
        }
    }

    fun orderedCrossover(first: String, second: String): String {
        val cityCount = first.length

        val subOne = Random.nextInt(0, cityCount)
        val subTwo = Random.nextInt(0, cityCount)

        val new = CharArray(cityCount)
        val used = mutableSetOf<Char>()

        for (i in min(subOne, subTwo) .. max(subOne, subTwo)) {
            new[i] = first[i]
            used.add(first[i])
        }

        val secondRemoved = ArrayDeque(second.toCharArray().toList().filter { it !in used })

        for (i in 0 until cityCount) {
            val current = new[i]
            if (current == Char.MIN_VALUE) { // null char that has not been initialized

                new[i] = secondRemoved.removeFirst()
            }
        }

        return new.joinToString("")
    }

    fun distanceOfPath(path: String): Int {
        var totalDistance = 0

        for (i in 0 until path.length - 1) {
            val distance = distances[path[i] - 'A'].getOrElse(path[i + 1] - 'A') { 0 }

            totalDistance += distance
        }

        totalDistance += distances[path[path.lastIndex] - 'A'][path[0] - 'A']

        return totalDistance
    }

    fun solve() {
        val population = Population(10, ::generator, ::orderedCrossover) { path -> ((1 / distanceOfPath(path).toFloat()) * 1000).roundToInt() }

        for (i in 0..9) {
            val mappedFitness = population.entities.map { entity -> Entity(entity.chromosome, distanceOfPath(entity.chromosome)) }
            val entityDistances = mappedFitness.map { entity -> entity.fitness }

            println("""
            Generation: ${population.generation}
            Mean: ${entityDistances.average()}
            Max: ${mappedFitness.maxBy { it.fitness }}
            Min: ${mappedFitness.minBy { it.fitness }}
        """.trimIndent())

            population.iterate()
        }
    }
}