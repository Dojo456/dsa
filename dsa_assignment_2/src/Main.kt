fun main() {
    print("Enter a message to test valid parenthesis: ")
    val input = readln()
    print("Your message ${if (isValidParenthesis(input)) "does" else "does NOT"} contain valid parenthesis.")
}

val matchingCloses = mapOf(
    '(' to ')',
    '{' to '}',
    '[' to ']',
)

fun isValidParenthesis(s: String): Boolean {
    val open = Stack<Char>()

    for (c in s) {
        if (matchingCloses.keys.contains(c)) {
            open.push(c)
        } else if (matchingCloses.values.contains(c)) {
            val currentOpen = open.pop()
            if (matchingCloses[currentOpen] != c) {
                return false
            }
        }
    }

    return open.pop() == null
}