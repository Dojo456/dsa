fun main() {
    val s = LinkedListStack<String>()

    s.push("daniel")
    s.push("im")
    s.push(",")
    s.push("world")
    s.push("hello")

    var current = s.pop()

    while (current != null) {
        println(current)
        current = s.pop()
    }
}