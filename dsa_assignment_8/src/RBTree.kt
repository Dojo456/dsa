import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.math.max
import kotlin.math.pow

class RBTree<T: Comparable<T>>(): BinaryTree<T> {
    object BTreePrinter { // copied this from https://stackoverflow.com/a/4973083/12021982
        private lateinit var output: PrintStream
        fun <T : Comparable<T>> printTree(tree: RBTree<T>): String {
            val root = tree.root
            val maxLevel = maxLevel(root)
            val stream = ByteArrayOutputStream()
            output = PrintStream(stream)

            printNodeInternal(listOf(root), 1, maxLevel)
            return stream.toString()
        }

        private fun <T : Comparable<T>> printNodeInternal(nodes: List<Node<T>?>, level: Int, maxLevel: Int): String {
            if (nodes.isEmpty() || isAllElementsNull(nodes)) return ""

            val floor = maxLevel - level
            val endLines = 2.0.pow((max((floor - 1).toDouble(), 0.0))).toInt()
            val firstSpaces = 2.0.pow(floor.toDouble()).toInt() - 1
            val betweenSpaces = 2.0.pow((floor + 1).toDouble()).toInt() - 1

            printWhitespaces(firstSpaces)

            val newNodes: MutableList<Node<T>?> = ArrayList()
            for (node in nodes) {
                if (node != null) {
                    output.print(node.value)
                    newNodes.add(node.left)
                    newNodes.add(node.right)
                } else {
                    newNodes.add(null)
                    newNodes.add(null)
                    output.print(" ")
                }

                printWhitespaces(betweenSpaces)
            }
            output.println("")

            for (i in 1..endLines) {
                for (j in nodes.indices) {
                    printWhitespaces(firstSpaces - i)
                    if (nodes[j] == null) {
                        printWhitespaces(endLines + endLines + i + 1)
                        continue
                    }

                    if (nodes[j]?.left != null) output.print("/")
                    else printWhitespaces(1)

                    printWhitespaces(i + i - 1)

                    if (nodes[j]?.right != null) output.print("\\")
                    else printWhitespaces(1)

                    printWhitespaces(endLines + endLines - i)
                }

                output.println("")
            }

            printNodeInternal(newNodes, level + 1, maxLevel)

            return output.toString()
        }

        private fun printWhitespaces(count: Int) {
            for (i in 0..<count) output.print(" ")
        }

        private fun <T : Comparable<T>> maxLevel(node: Node<T>?): Int {
            if (node == null) return 0

            return (max(
                maxLevel(node.left).toDouble(),
                maxLevel(node.right).toDouble()
            ) + 1).toInt()
        }

        private fun <T> isAllElementsNull(list: List<T?>): Boolean {
            for (`object` in list) {
                if (`object` != null) return false
            }

            return true
        }
    }

    override fun lookup(value: T): Boolean {
        if (root == null) return false

        fun traverse(current: Node<T>, compareTo: Node<T>): Boolean {
            return if (compareTo > current) {
                if (current.right != null) {
                    traverse(current.right!!, compareTo)
                } else {
                    false
                }
            } else if (compareTo < current) {
                if (current.left != null) {
                    traverse(current.left!!, compareTo)
                } else {
                    false
                }
            } else { // nodes are equal
                true
            }
        }

        return traverse(root!!, Node(value, true))
    }

    override fun toString(): String {
        return BTreePrinter.printTree(this)
    }

    class Node<T: Comparable<T>>(val value: T, var red: Boolean, var left: Node<T>? = null, var right: Node<T>? = null): Comparable<Node<T>> {
        var black: Boolean
            get() = !red
            set(value) {
                red = !value
            }
        override fun compareTo(other: Node<T>): Int {
            return value.compareTo(other.value)
        }
    }

    private var root: Node<T>? = null

    constructor(rootValue: T) : this() {
        root = Node(rootValue, true)
    }

    override fun insert(value: T): Boolean {
        val node = Node(value, true)

        fun traverse(current: Node<T>, toInsert: Node<T>): Boolean {
            if (toInsert > current) {
                if (current.right != null) {
                    return traverse(current.right!!, toInsert)
                } else {
                    current.right = toInsert
                    return true
                }
            } else if (toInsert < current) {
                if (current.left != null) {
                    return traverse(current.left!!, toInsert)
                } else {
                    current.left = toInsert
                    return true
                }
            } else { // node already exists
                return false
            }
        }

        if (root != null) {
            val result = traverse(root!!, node)
            resolveInvariants()
            return result
        } else {
            root = node
            return true
        }
    }


    private fun resolveInvariants() {
        return
    }
}