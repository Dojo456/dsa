import org.example.TravellingSalesperson
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class TravellingSalespersonTest {

    @Test
    fun generator() {
        val new = TravellingSalesperson.generator()

        checkValidPath(new, listOf("A", "B", "C", "D", "E"))
    }

    @Test
    fun orderedCrossover() {
        val cities = listOf("A", "B", "C", "D", "E", "F", "G", "H")

        val first = cities.shuffled().joinToString("")
        val second = cities.shuffled().joinToString("")
        val child = TravellingSalesperson.orderedCrossover(first, second)

        checkValidPath(child, cities)
    }

    private fun checkValidPath(child: String, cities: List<String>) {
        var containsAll = true
        cities.forEach { containsAll = containsAll && child.contains(it) }
        assertTrue(containsAll)
    }

    @Test
    fun distanceOfPath() {
        assertEquals(34, TravellingSalesperson.distanceOfPath("BEDCA"))
        assertEquals(38, TravellingSalesperson.distanceOfPath("ACBED"))
        assertEquals(32, TravellingSalesperson.distanceOfPath("EACBD"))
    }
}