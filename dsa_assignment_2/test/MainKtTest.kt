import org.junit.jupiter.api.Test

class MainKtTest {

    @Test
    fun isValidParenthesis() {
        assert(isValidParenthesis("(hello)"))
        assert(isValidParenthesis("(hello{hello})"))
        assert(isValidParenthesis("(hello(sdf[sdf{sdf}sdf]sdf)sdf)"))
        assert(!isValidParenthesis("(hello(sdf[sdf{sdf}sdf]sdf)sdf"))
        assert(!isValidParenthesis("(hello(sdf[sdf{sdf}sdf]sdf)sdf}"))
    }
}