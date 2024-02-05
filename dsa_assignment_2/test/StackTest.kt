import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.random.Random

class StackTest {
    private var stack = Stack<String>()

    @BeforeEach
    fun setUp() {
        stack = Stack()
    }

    @Test
    fun push() {
        var data: String = Random.nextInt().toString()
        for (i in 1..5) {
            data = Random.nextInt().toString()
            stack.push(data)
        }

        assert(stack.pop() == data)
    }

    @Test
    fun pop() {
        val elements = mutableListOf<String>()

        for (i in 1..5) {
            elements.add(Random.nextInt().toString())
        }

        for (element in elements) {
            stack.push(element)
        }

        var index = 4
        do {
            val element = stack.pop()
            assert(element == elements[index])
            index--
        } while (element != null && index > 0)
    }

    @Test
    fun peek() {
        val elements = mutableListOf<String>()

        for (i in 1..5) {
            elements.add(Random.nextInt().toString())
        }

        for (element in elements) {
            stack.push(element)
        }

        var index = 4
        do {
            val element = stack.peek()
            assert(element == elements[index])
            stack.pop()
            index--
        } while (element != null && index > 0)
    }

    @Test
    fun isEmpty() {
        assert(stack.isEmpty())
        stack.push(Random.nextInt().toString())
        assert(!stack.isEmpty())
    }
}