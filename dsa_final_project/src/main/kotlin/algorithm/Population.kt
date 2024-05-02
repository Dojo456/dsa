package org.example.algorithm

import kotlin.random.Random

class Population(
    private val entityCount: Int,
    generator: () -> String,
    val crossover: (String, String) -> String,
    val fitness: (String) -> Int
) {
    val entities = mutableListOf<Entity>()
    var generation = 0
        private set

    init {
        for (i in 0 until entityCount) {
            val chromosome = generator()
            entities.add(Entity(chromosome, fitness(chromosome)))
        }

        println(entities.size)
    }

    fun iterate() {
        // sample entities based on fitness using roulette wheel sampling

        val sample = fun(not: Entity?): Entity {
            val sampleFrom = entities.filter { it != not }

            val sum = sampleFrom.sumOf { entity -> entity.fitness }
            val offset = (Random.nextFloat() * sum).toInt()
            val sampleFitness = (Random.nextFloat() * sum).toInt()

            var cumFitness = 0

            sampleFrom.forEach { entity ->
                if (cumFitness > (offset + sampleFitness) % sum) {
                    return entity
                }
                cumFitness += entity.fitness
            }

            return entities.last()
        }

        val newPool = mutableListOf<Entity>()

        while (newPool.size < entityCount) {
            val first = sample(null)
            // ensures that second is not equal to first
            val second = sample(first)

            val new = StringBuilder(crossover(first.chromosome, second.chromosome))

            // mutate the new offspring if needed
            if (Random.nextFloat() < 0.1) { // mutation is simply swap two cities in a path
                val i = Random.nextInt(0, new.length)
                val j = Random.nextInt(0, new.length)

                val temp = new[i]

                new[i] = new[j]
                new[j] = temp
            }

            val newString = new.toString()

            newPool.add(Entity(newString, fitness(newString)))
        }

        entities.clear()
        entities.addAll(newPool)
        generation += 1
    }
}