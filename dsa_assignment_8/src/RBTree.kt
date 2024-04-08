import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.math.max
import kotlin.math.pow

class RBTree(): BinaryTree {
    object BTreePrinter { // copied this from https://stackoverflow.com/a/4973083/12021982
        private lateinit var output: PrintStream
        fun printTree(tree: RBTree): String {
            val root = tree.root
            val maxLevel = maxLevel(root)
            val stream = ByteArrayOutputStream()
            output = PrintStream(stream)

            printNodeInternal(listOf(root), 1, maxLevel)
            return stream.toString()
        }

        private fun printNodeInternal(nodes: List<Node>, level: Int, maxLevel: Int): String {
            if (nodes.isEmpty() || isAllElementsNull(nodes)) return ""

            val floor = maxLevel - level
            val endLines = 2.0.pow((max((floor - 1).toDouble(), 0.0))).toInt()
            val firstSpaces = 2.0.pow(floor.toDouble()).toInt() - 1
            val betweenSpaces = 2.0.pow((floor + 1).toDouble()).toInt() - 1

            printWhitespaces(firstSpaces)

            val newNodes: MutableList<Node> = ArrayList()
            for (node in nodes) {
                if (node != Node.NIL) {
                    // Everything after this is in red
                    val red = "\u001b[31m"

                    // Resets previous color codes
                    val reset = "\u001b[0m"

                    output.print(if (node.color == Node.Color.RED) "$red${node.value}$reset" else "${node.value}")
                    newNodes.add(node.left)
                    newNodes.add(node.right)
                } else {
                    newNodes.add(Node.NIL)
                    newNodes.add(Node.NIL)
                    output.print(" ")
                }

                printWhitespaces(betweenSpaces)
            }
            output.println("")

            for (i in 1..endLines) {
                for (j in nodes.indices) {
                    printWhitespaces(firstSpaces - i)
                    if (nodes[j] == Node.NIL) {
                        printWhitespaces(endLines + endLines + i + 1)
                        continue
                    }

                    if (nodes[j].left != Node.NIL) output.print("/")
                    else printWhitespaces(1)

                    printWhitespaces(i + i - 1)

                    if (nodes[j].right != Node.NIL) output.print("\\")
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

        private fun maxLevel(node: Node?): Int {
            if (node == null) return 0

            return (max(
                maxLevel(node.left).toDouble(),
                maxLevel(node.right).toDouble()
            ) + 1).toInt()
        }

        private fun isAllElementsNull(list: List<Node>): Boolean {
            for (`object` in list) {
                if (`object` != Node.NIL) return false
            }

            return true
        }
    }

    override fun lookup(value: Int): Boolean {
        if (root == Node.NIL) return false

        fun traverse(current: Node, compareTo: Node): Boolean {
            return if (compareTo > current) {
                if (current.right != Node.NIL) {
                    traverse(current.right, compareTo)
                } else {
                    false
                }
            } else if (compareTo < current) {
                if (current.left != Node.NIL) {
                    traverse(current.left, compareTo)
                } else {
                    false
                }
            } else { // nodes are equal
                true
            }
        }

        return traverse(root, Node(value, Node.Color.BLACK))
    }

    override fun toString(): String {
        return BTreePrinter.printTree(this)
    }

    private enum class Direction {
        LEFT, RIGHT;

        fun other(): Direction {
            if (this == LEFT) {
                return RIGHT
            }

            return LEFT
        }
    }

    private open class Node(open val value: Int, open var color: Color): Comparable<Node> {
        var parent: Node? = null
        enum class Color {
            RED, BLACK
        }

        object NIL: Node(Int.MAX_VALUE, Color.BLACK) { // special instance of Node to represent nil and is always black
            override var color: Color
                get() = Color.BLACK
                set(_) {}

            override fun compareTo(other: Node): Int {
                throw IllegalStateException("cannot compare NIL node")
            }

            override fun equals(other: Any?): Boolean {
                return other === this
            }

            override fun hashCode(): Int {
                return 0
            }
        }

        var left: Node = NIL
        var right: Node  = NIL

        override fun equals(other: Any?): Boolean {
            if (other is NIL) {
                return other == this
            } else if (other is Node) { // node is equal if value is equal and children are equal
                return (this.value == other.value) && (this.left == other.left) && (this.right == other.right)
            }

            return false
        }

        fun childAt(direction: Direction): Node {
            if (direction == Direction.LEFT) {
                return left
            }

            return right
        }

        fun setChildAt(direction: Direction, node: Node) {
            if (direction == Direction.LEFT) {
                left = node
            } else {
                right = node
            }
        }

        override fun compareTo(other: Node): Int {
            return value.compareTo(other.value)
        }

        override fun hashCode(): Int {
            var result = value
            result = 31 * result + color.hashCode()
            result = 31 * result + left.hashCode()
            result = 31 * result + right.hashCode()
            return result
        }
    }

    private var root: Node = Node.NIL

    constructor(rootValue: Int) : this() {
        root = Node(rootValue, Node.Color.BLACK)
    }

    override fun insert(value: Int): Boolean {
        val newNode = Node(value, Node.Color.RED)

        fun traverse(current: Node, toInsert: Node): Boolean {
            if (toInsert > current) {
                if (current.right != Node.NIL) {
                    return traverse(current.right, toInsert)
                } else {
                    current.right = toInsert
                    toInsert.parent = current
                    return true
                }
            } else if (toInsert < current) {
                if (current.left != Node.NIL) {
                    return traverse(current.left, toInsert)
                } else {
                    current.left = toInsert
                    toInsert.parent = current
                    return true
                }
            } else { // node already exists
                return false
            }
        }

        if (root != Node.NIL) {
            val result = traverse(root, newNode)
            insertFixup(newNode)
            return result
        } else {
            root = newNode
            return true
        }
    }


    private fun insertFixup(inserted: Node) {
        // copied this a little from the given python code
        var z = inserted

        while (z.parent?.color == Node.Color.RED) {
            if (z.parent === z.parent?.parent?.left) { // if parent is left child
                val y = z.parent!!.parent!!.right
                if (y.color == Node.Color.RED) { // if uncle is red
                    z.parent!!.color = Node.Color.BLACK
                    y.color = Node.Color.BLACK
                    z.parent!!.parent!!.color = Node.Color.RED
                } else {
                    if (z === z.parent!!.right) {
                        z = z.parent!!
                        rotate(z, Direction.LEFT)
                    }
                    z.parent!!.color = Node.Color.BLACK
                    z.parent!!.parent?.color = Node.Color.RED
                    rotate(z.parent!!.parent!!, Direction.RIGHT)
                }
            } else { // parent is right child
                val y = z.parent?.parent?.left
                if (y?.color == Node.Color.RED) { // if uncle is red
                    z.parent!!.color = Node.Color.BLACK
                    y.color = Node.Color.BLACK
                    z.parent?.parent?.color = Node.Color.RED
                    z = z.parent!!.parent!!
                } else {
                    if (z == z.parent!!.left) {
                        z = z.parent!!
                        rotate(z, Direction.RIGHT)
                    }
                    z.parent!!.color = Node.Color.BLACK
                    z.parent?.parent?.color = Node.Color.RED
                    z.parent?.parent?.run {rotate(z.parent!!.parent!!, Direction.LEFT) }
                }
            }

            if (z == root) { // case 0
                break
            }
        }

        root.color = Node.Color.BLACK
    }

    private fun rotate(around: Node, direction: Direction) {
        val y = around.childAt(direction.other())
        around.setChildAt(direction.other(), y.childAt(direction))

        if (y.childAt(direction) == Node.NIL) {
            y.childAt(direction).parent = around
        }

        y.parent = around.parent

        if (around.parent == null) {
            root = y
        } else if (around == around.parent?.left) {
            around.parent?.left = y
        } else {
            around.parent?.right = y
        }

        y.setChildAt(direction, around)
        around.parent = y
    }

    override fun equals(other: Any?): Boolean {
        if (other is RBTree) {
            return root == other.root
        }

        return false
    }

    override fun hashCode(): Int {
        return root.hashCode()
    }
}