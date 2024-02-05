import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.random.Random

class LinkedListTest {
    private var list = LinkedList<Int>()

    @BeforeEach
    fun setUp() {
        list = LinkedList()
    }

    @Test
    fun pushFront() {
        val data = Random.nextInt()
        list.pushFront(data)
        assert(list.popFront() == data)
    }

    @Test
    fun pushBack() {
        val data = Random.nextInt()
        list.pushBack(data)
        assert(list.popBack() == data)
    }

    @Test
    fun popFront() {
        val data = Random.nextInt()
        list.pushFront(data)
        assert(list.popFront() == data)
    }

    @Test
    fun popBack() {
        val data = Random.nextInt()
        list.pushBack(data)
        assert(list.popBack() == data)
    }

    @Test
    fun peekFront() {
        val data = Random.nextInt()
        list.pushFront(data)
        assert(list.peekFront() == data)
    }

    @Test
    fun peekBack() {
        val data = Random.nextInt()
        list.pushBack(data)
        assert(list.peekBack() == data)
    }

    @Test
    fun isEmpty() {
        assert(list.isEmpty())
        list.pushFront(Random.nextInt())
        assert(!list.isEmpty())
    }
}