class Matrix(private val size: Int) {
     private var values: Array<Array<Int>>
     private var square = true

     init {
         values = Array(size){ Array(size){ 0 } }
     }

    constructor(values: Array<Array<Int>>): this(values.size) {
        this.values = values
        square = false
    }

    operator fun get(x: Int, y: Int): Int {
        return values[x][y]
    }

    operator fun set(x: Int, y: Int, value: Int) {
        values[x][y] = value
    }

    fun normalTimes(by: Matrix): Matrix {
        if (size != by.size) {
            throw IllegalArgumentException("incompatible sizes")
        }

        val result = Matrix(size)

        for (i in (0..<size)) {
            for (j in (0..<by.size)) {
                var sum = 0

                for (k in (0..<size)) {
                    val thisValue = values[i][k]
                    val byValue = by.values[k][j]

                    sum += thisValue * byValue
                }

                result[i,j] = sum
            }
        }

        return result
    }

    operator fun plus(by: Matrix): Matrix {
        val result = Matrix(size)

        for (i in values.indices) {
            for (j in values.indices) {
                result[i,j] = this[i, j] + by[i, j]
            }
        }

        return result
    }

    private operator fun minus(by: Matrix): Matrix {
        val result = Matrix(size)

        for (i in values.indices) {
            for (j in values.indices) {
                result[i,j] = this[i, j] - by[i, j]
            }
        }

        return result
    }

    /**
     * Multiply using Strassen's algorithm
     */
    operator fun times(by: Matrix): Matrix {
        if (size != by.size) {
            throw IllegalArgumentException("mismatched sizes")
        }

        if (size == 1) { // base case
            return Matrix(arrayOf(arrayOf(values[0][0] * by.values[0][0])))
        }

        val a = Array(2) {Array(2) {Matrix(size/2)} }
        val b = Array(2) {Array(2) {Matrix(by.size/2)} }

        fun partition(from: Matrix, source: Array<Array<Matrix>>) {
            for (i in 0..1) {
                for (j in 0..1) {
                    val halfSize = size / 2
                    val start = { num: Int -> num * halfSize }
                    val end = {num: Int -> (num + 1) * halfSize}

                    val values = from.values.slice(start(i)..<end(i)).map { row -> row.slice(start(j)..<end(j)).toTypedArray() }

                    source[i][j] = Matrix(values.toTypedArray())
                }
            }
        }

        partition(this, a)
        partition(by, b)

        val a11 = a[0][0]
        val a12 = a[0][1]
        val a21 = a[1][0]
        val a22 = a[1][1]

        val b12 = b[0][1]
        val b11 = b[0][0]
        val b21 = b[1][0]
        val b22 = b[1][1]

        val m1 = (a11 + a22) * (b11 + b22)
        val m2 = (a21 + a22) * b11
        val m3 = a11 * (b12 - b22)
        val m4 = a22 * (b21 - b11)
        val m5 = (a11 + a12) * b22
        val m6 = (a21 - a11) * (b11 + b12)
        val m7 = (a12 - a22) * (b21 + b22)


        val c = Array(2){Array(2){Matrix(size / 2)} }

        c[0][0] = m1 + m4 - m5 + m7
        c[0][1] = m3 + m5
        c[1][0] = m2 + m4
        c[1][1] = m1 - m2 + m3 + m6

        return c[0][0].hStack(c[0][1]).vStack(c[1][0].hStack(c[1][1]))
    }

    operator fun times(by: Int): Matrix {
        val resultValues = Array(size) {Array(size) { 0 } }

        for (i in values.indices) {
            for (j in values.indices) {
                resultValues[i][j] = values[i][j] * by
            }
        }

        return Matrix(resultValues)
    }

    private fun hStack(with: Matrix): Matrix {
        val resultValues = Array(size){ emptyArray<Int>()}

        for (i in values.indices) {
            resultValues[i] = values[i] + with.values[i]
        }

        return Matrix(resultValues)
    }

    private fun vStack(with: Matrix): Matrix {
        if (size != with.size) {
            throw IllegalArgumentException("mismatched sizes")
        }

        val resultValues = values + with.values

        return Matrix(resultValues)
    }

    override fun toString(): String {
        return (values.mapIndexed { i, row -> row.joinToString(separator = "", prefix = i.toString(), postfix = "\n"){cell -> "[$cell]"} }).joinToString(separator = "")
    }

    override fun equals(other: Any?): Boolean {
        if (other is Matrix) {
            return values.contentDeepEquals(other.values)
        }

        return false
    }
}