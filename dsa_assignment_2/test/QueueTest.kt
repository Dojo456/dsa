import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.random.Random

class QueueTest {
    private var queue = Queue<String>()

    @BeforeEach
    fun setUp() {
        queue = Queue()
    }

    @Test
    fun enqueue() {
        val first: String = Random.nextInt().toString()
        queue.enqueue(first)
        for (i in 1..5) {
            queue.enqueue(Random.nextInt().toString())
        }

        assert(queue.peek() == first)
    }


    @Test
    fun dequeue() {
        val elements = mutableListOf<String>()

        for (i in 1..5) {
            elements.add(Random.nextInt().toString())
        }

        for (element in elements) {
            queue.enqueue(element)
        }

        var index = 0
        do {
            val element = queue.dequeue()
            assert(element == elements[index])
            index++
        } while (element != null && index < 5)
    }

    @Test
    fun peek() {
        val elements = mutableListOf<String>()

        for (i in 1..5) {
            elements.add(Random.nextInt().toString())
        }

        for (element in elements) {
            queue.enqueue(element)
        }

        var index = 0
        do {
            val element = queue.peek()
            assert(element == elements[index])
            queue.dequeue()
            index++
        } while (element != null && index < 5)
    }

    @Test
    fun isEmpty() {
        assert(queue.isEmpty())
        queue.enqueue(Random.nextInt().toString())
        assert(!queue.isEmpty())
    }
}