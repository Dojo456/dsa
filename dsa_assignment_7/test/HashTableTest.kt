import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

/**
 * Tests for [HashTableTest] class. Tests are only on the methods defined in [AssociativeArray] interface.
 */
class HashTableTest {
    private var table = HashTable<String, Int>()

    @BeforeEach
    fun setUp() {
        table = HashTable()
    }

    @org.junit.jupiter.api.Test
    fun set() {
        // simply testing setting values and getting
        table["daniel"] = 19
        table["kelly"] = 20

        assertEquals(19, table["daniel"])
        assertEquals(20, table["kelly"])
    }

    @org.junit.jupiter.api.Test
    fun contains() {
        table["daniel"] = 19
        table["kelly"] = 20

        assertTrue(table.contains("daniel"))
        assertTrue(table.contains("kelly"))
        assertFalse(table.contains("kathy"))
    }

    @org.junit.jupiter.api.Test
    fun get() {
        table["daniel"] = 19
        table["kelly"] = 20

        assertEquals(19, table["daniel"])
        assertEquals(20, table["kelly"])
    }

    @org.junit.jupiter.api.Test
    fun remove() {
        table["daniel"] = 19
        table["kelly"] = 20

        assertEquals(19, table["daniel"])
        assertEquals(20, table["kelly"])

        table.remove("daniel")
        assertNull(table["daniel"])
    }

    @org.junit.jupiter.api.Test
    fun size() {
        table["daniel"] = 19
        table["kelly"] = 20

        assertEquals(2, table.size())

        table["kathy"] = 20
        assertEquals(3, table.size())

        table.remove("kathy")
        assertEquals(2, table.size())
    }

    @org.junit.jupiter.api.Test
    fun keyValuePairs() {
        table["daniel"] = 19
        table["kelly"] = 20

        // value comparison works when comparing lists and pairs
        assertEquals(listOf(Pair("daniel", 19), Pair("kelly", 20)), table.keyValuePairs())
    }
}