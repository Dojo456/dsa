fun main() {
    val tree = RBTree(2)
    tree.insert(0)
    tree.insert(4)
    tree.insert(1)
    tree.insert(3)
    tree.insert(5)
    tree.insert(-1)

    println(tree)
}