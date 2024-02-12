import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class PriorityQueueTest {
    private var queue = PriorityQueue<String>()

    @BeforeEach
    fun setUp() {
        queue = PriorityQueue()
    }

    @Test
    fun isEmpty() {
        assertTrue(queue.isEmpty())
        queue.addWithPriority("Orlando", 0.0)
        assertFalse(queue.isEmpty())
    }

    @Test
    operator fun next() {
        queue.addWithPriority("Orlando", 0.0)
        queue.addWithPriority("Boston", 1.0)
        queue.addWithPriority("LA", 3.0)

        assertEquals("Orlando", queue.next())
    }

    @Test
    fun adjustPriority() {
        queue.addWithPriority("Orlando", 1.0)
        queue.addWithPriority("Boston", 2.0)
        queue.addWithPriority("LA", 3.0)

        queue.adjustPriority("LA", 0.0)

        assertEquals("LA", queue.next())
    }

    @Test
    fun addWithPriority() {
        queue.addWithPriority("Orlando", 0.0)
        queue.addWithPriority("Boston", 1.0)
        queue.addWithPriority("LA", 3.0)

        assertEquals("Orlando", queue.next())
        assertEquals("Boston", queue.next())
        assertEquals("LA", queue.next())
    }
}