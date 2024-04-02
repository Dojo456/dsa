import kotlin.math.absoluteValue

/**
 * Represents a mapping of keys to values.
 * @param K the type of the keys
 * @param V the type of the values
 */
interface AssociativeArray<K, V> {
    /**
     * Insert the mapping from the key, [k], to the value, [v].
     * If the key already maps to a value, replace the mapping.
     */
    operator fun set(k: K, v: V)

    /**
     * @return true if [k] is a key in the associative array
     */
    operator fun contains(k: K): Boolean

    /**
     * @return the value associated with the key [k] or null if it doesn't exist
     */
    operator fun get(k: K): V?

    /**
     * Remove the key, [k], from the associative array
     * @param k the key to remove
     * @return true if the item was successfully removed and false if the element was not found
     */
    fun remove(k: K): Boolean

    /**
     * @return the number of elements stored in the hash table
     */
    fun size(): Int

    /**
     * @return the full list of key value pairs for the associative array
     */
    fun keyValuePairs(): List<Pair<K, V>>
}

class HashTable<K, V>(): AssociativeArray<K, V> {
    constructor(bucketSizes: List<Int>): this() { // secondary constructor to use for testing
        primes = bucketSizes
        initArrays()
    }

    private fun initArrays() {
        data = MutableList(primes[primeIndex]) {
            mutableListOf()
        }
        collisions = MutableList(primes[primeIndex]) {0}
    }

    private var primeIndex = 0

    private val keys = mutableSetOf<K>()
    private var primes = listOf(53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593)
    private lateinit var data: MutableList<MutableList<Pair<K, V>>>

    lateinit var collisions: MutableList<Int>
        private set
    private var size = 0

    init {
        initArrays()
    }

    /**
     * [checkResize] checks if a resize is needed based on [collisions]. If [collisions] divided by [size] is greater than 3, resize is needed and it performs one.
     */
    private fun checkResize() {
        if (collisions.average() > 3) {
            val pairs = keyValuePairs()

            // increase the prime being used and reinsert every pair
            if (primeIndex + 1 < primes.size) primeIndex += 1

            keys.clear()
            initArrays()

            for (pair in pairs) {
                this[pair.first] = pair.second
            }
        }
    }

    private fun indexOf(k: K): Int {
        return k.hashCode().absoluteValue % primes[primeIndex]
    }

    private fun bucketAt(k: K): MutableList<Pair<K, V>> {
        return data[indexOf(k)]
    }

    override fun set(k: K, v: V) {
        val bucket = bucketAt(k)

        for (item in bucket) {
            if (item.first == k) { // item already exists
                return
            }
        }

        if (bucket.size > 0) {
            collisions[indexOf(k)] += 1 // if bucket is not empty, that is collision
        }
        size += 1
        bucket.add(Pair(k, v))
        keys.add(k)
        checkResize()
    }

    override fun contains(k: K): Boolean {
        for (item in bucketAt(k)) {
            if (item.first == k) {
                return true
            }
        }

        return false
    }

    override fun get(k: K): V? {
        for (item in bucketAt(k)) {
            if (item.first == k) {
                return item.second
            }
        }

        return null
    }

    override fun remove(k: K): Boolean {
        var removeIndex: Int? = null

        val bucket = bucketAt(k)

        bucket.forEachIndexed {
            index, item ->
            if (item.first == k) {
                removeIndex = index
            }
        }

        removeIndex?.run {
            if (bucket.size > 1) collisions -= 1
            size -= 1
            bucket.removeAt(this)
            keys.remove(k)
            return true
        }

        return false
    }

    override fun size(): Int {
        return size
    }

    override fun keyValuePairs(): List<Pair<K, V>> {
        val pairs = mutableListOf<Pair<K, V>>()

        val indexes = setOf<Int>()

        for (key in keys) {
            val index = indexOf(key)

            if (!indexes.contains(index)) {
                pairs += data[index]
                indexes.plus(index)
            }
        }

        return pairs
    }

}