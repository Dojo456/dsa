import org.junit.jupiter.api.Assertions.*

class RBTreeTest {

    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
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
}