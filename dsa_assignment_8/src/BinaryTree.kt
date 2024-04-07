/**
 * Data structure that allows for fast lookup of data that can be sorted.
 * @param T the type of the data to be stored.
 */
interface BinaryTree<T: Comparable<T>> {
    /**
     * Inserts a new value to the binary tree at the appropriate position.
     * @param value value to be inserted.
     * @return True if a value was successfully inserted. False if it was not, i.e. it already exists.
     */
    fun insert(value: T): Boolean

    /**
     * Check to see if the specified element is within this binary tree.
     * @param value the value to be looked-up.
     * @return True if value exists within tree, False if it does not.
     */
    fun lookup(value: T): Boolean
}