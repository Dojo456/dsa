import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.random.Random

class RBTreeTest {

    @Test
    fun insert() {
        val tree = RBTree(2)
        tree.insert(0)
        tree.insert(1)
        tree.insert(3)

        assertTrue(tree.lookup(0))
        assertTrue(tree.lookup(1))
        assertFalse(tree.lookup(4))
        assertFalse(tree.lookup(5))
    }

    @Test
    fun lookup() {
        val tree = RBTree(2)
        tree.insert(0)
        tree.insert(1)
        tree.insert(3)

        assertTrue(tree.lookup(0))
        assertTrue(tree.lookup(1))
        assertFalse(tree.lookup(4))
        assertFalse(tree.lookup(5))
    }

    @Test
    fun balance() {
        val tree = RBTree()
        for (i in 0..10) {
            tree.insert((Random.nextFloat() * 100).toInt())
        }

        println(tree)
    }
}