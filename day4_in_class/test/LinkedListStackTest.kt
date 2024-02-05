import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class LinkedListStackTest {
    private lateinit var s: LinkedListStack<String>

    @BeforeEach
    fun setup() {
        s = LinkedListStack()
    }

    @Test
    fun push() {
        s.push("hello")
        s.push("world")
        assertEquals(s.peek(), "world")
    }

    @Test
    fun pop() {
        s.push("hello")
        s.push("world")

        assertEquals(s.pop(), "world")
        assertEquals(s.pop(), "hello")
        assertEquals(s.pop(), null)
    }

    @Test
    fun peek() {
        s.push("hello")
        s.push("world")
        assertEquals(s.peek(), "world")
    }

    @Test
    fun isEmpty() {
        assert(s.isEmpty())
        s.push("hello")
        assert(!s.isEmpty())
    }
}