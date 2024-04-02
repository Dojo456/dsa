import kotlin.math.pow
import kotlin.random.Random

fun main() {
    // Test case 1, list of 1,000 random numbers
    println("Case 1")
    testCollisions(List(1000){Random.nextInt()})
    println()

    // Test case 2, list of 1,000 numbers ascending
    println("Case 2")
    testCollisions((0..1000).toList())
}

fun testCollisions(numbers: List<Int>) {
    val bucketSizes = List(10) { 2.0.pow(it + 6).toInt() }

    val primeHashTable = HashTable<Int, Boolean>()
    val customBucketSize = HashTable<Int, Boolean>(bucketSizes)

    for (num in numbers) {
        primeHashTable[num] = true
        customBucketSize[num] = true
    }

    println("With primes ${"%.2f".format(primeHashTable.collisions.average())}")
    println("With custom size ${"%.2f".format(customBucketSize.collisions.average())}")
}