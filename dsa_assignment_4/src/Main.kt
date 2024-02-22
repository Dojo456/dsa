import java.io.FileOutputStream
import java.io.OutputStream

fun main() {
    val analyzer = Analyzer()
    val measurements = analyzer.measureAll()

    println(measurements)

    FileOutputStream("results.csv").apply { writeCsv(measurements) }
}

fun OutputStream.writeCsv(measurements: Map<String, Map<Int, Int>>) {
    val writer = bufferedWriter()
    writer.write(""""Algorithm", "Size", "Time"""")
    writer.newLine()
    measurements.forEach {alg ->
        val sortName = alg.key
        alg.value.forEach {
            writer.write("\"$sortName Sort\", ${it.key}, ${it.value}")
            writer.newLine()
        }
    }
    writer.flush()
}
