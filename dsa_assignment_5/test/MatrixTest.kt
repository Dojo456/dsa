import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class MatrixTest {
    private var matrix: Matrix = Matrix(2)
    @BeforeEach
    fun setUp() {
        matrix = Matrix(16)
    }

    @Test
    fun get() {
        matrix[2,5] = 10

        assertEquals(10, matrix[2,5])

        val first = Matrix(
            arrayOf(
                arrayOf(0, 1, 3, 4),
                arrayOf(2, 7, 8, 5),
                arrayOf(12, 10, 9, 5),
                arrayOf(12, 10, 9, 5),
            )
        )

        assertEquals(8, first[1,2])
    }

    @Test
    fun multiply() {
        val first = Matrix(
            arrayOf(
                arrayOf(0, 1, 3, 4),
                arrayOf(2, 7, 8, 5),
                arrayOf(12, 10, 9, 5),
                arrayOf(12, 10, 9, 5),
            )
        )

        val second = Matrix(
            arrayOf(
                arrayOf(0, 1, 3, 4),
                arrayOf(2, 7, 8, 5),
                arrayOf(12, 10, 9, 5),
                arrayOf(12, 10, 9, 5),
            )
        )

        assertEquals(first.normalTimes(second), first * second)
    }
}