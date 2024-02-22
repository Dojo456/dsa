import java.util.*

object SortingShenanigans {
    // Pretty colors :)
    val ANSI_RESET: String = "\u001B[0m"
    val ANSI_RED: String = "\u001B[31m"
    val ANSI_GREEN: String = "\u001B[32m"
    val ANSI_YELLOW: String = "\u001B[33m"
    val ANSI_BLUE: String = "\u001B[34m"
    val ANSI_PURPLE: String = "\u001B[35m"
    val ANSI_CYAN: String = "\u001B[36m"
    val ANSI_WHITE: String = "\u001B[37m"

    val colors: Array<String> = arrayOf(
        ANSI_PURPLE, ANSI_BLUE, ANSI_CYAN,
        ANSI_GREEN, ANSI_YELLOW, ANSI_RED,
    )

    val sorts: Array<String> = arrayOf(
        "Selection Sort", "Quick Sort", "Merge Sort", "Radix Sort"
    )

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        //UI GOES HERE

        var items: ArrayList<Int>
        val `in` = Scanner(System.`in`)
        var input = ""
        var inputStage = 0

        var selectedSort = "None"
        var totalElements = 0
        var totalRuns = 0

        var start: Long
        var end: Long
        var totalTime: Long = 0

        var analyzer: Analyzer

        while (inputStage < 7) {
            // Necessary loop in order to keep prompting user if
            // bad input was given


            // First stage. Asks for a sort


            if (inputStage == 0 || inputStage == 1) {
                sysClear()
                for (i in sorts.indices) {
                    println(
                        "[" + colors[Arrays.asList(*sorts).indexOf(
                            sorts[i]
                        )] + (i + 1) + ANSI_RESET + "] " + sorts[i]
                    )
                }

                println()

                if (inputStage == 0) {
                    println("Which sort would you like to analyze? [1-${sorts.size}] " + ANSI_PURPLE)
                } else {
                    println(ANSI_RED + "Invalid input" + ANSI_RESET + ".")
                    println("Which sort would you like to analyze? [1-${sorts.size}] " + ANSI_PURPLE)
                }

                input = `in`.nextLine()

                // If the string only contains integers 1-7 (there are only 7 sorts)
                if (input.matches("[1-7]".toRegex())) {
                    inputStage = 2
                    selectedSort = sorts[input.toInt() - 1]
                } else {
                    inputStage = 1
                }
            } else if (inputStage == 2 || inputStage == 3) {
                sysClear()
                println(
                    "Selected Sort: " + colors[Arrays.asList(*sorts).indexOf(selectedSort)] + selectedSort + ANSI_RESET
                )
                println()

                if (inputStage == 3) {
                    println(ANSI_RED + "Invalid input" + ANSI_RESET + ".")
                }
                println("How many elements would you like to generate? " + ANSI_PURPLE)
                input = `in`.nextLine()

                // If the string only contains integers 0-9
                if (input.matches("[0-9]+".toRegex())) {
                    totalElements = input.toInt()
                    inputStage = 4
                } else {
                    inputStage = 3
                }
            } else if (inputStage == 4 || inputStage == 5) {
                sysClear()
                println(
                    "Selected Sort: " + colors[Arrays.asList(*sorts).indexOf(selectedSort)] + selectedSort + ANSI_RESET
                )
                println("Total Elements: " + ANSI_WHITE + totalElements + ANSI_RESET)
                println()

                if (inputStage == 5) {
                    println(ANSI_RED + "Invalid input" + ANSI_RESET + ".")
                }
                println("How many test runs would you like to conduct? " + ANSI_PURPLE)
                input = `in`.nextLine()

                // If the string only contains integers 0-9
                if (input.matches("[0-9]+".toRegex())) {
                    totalRuns = input.toInt()
                    inputStage = 6
                } else {
                    inputStage = 5
                }
            } else if (inputStage == 6) {
                println(ANSI_RESET + "Generating Elements...")
                items = generateRandomNumbers(totalElements)

                var runCount: Long = 1
                for (i in 0 until totalRuns + 1) {
                    // Shuffle items
                    if (i == 1) {
                        continue
                    }
                    print("\nReshuffling Arraylist...")
                    items.shuffle()
                    println(
                        "\n" + colors[Arrays.asList(*sorts)
                            .indexOf(selectedSort)] + selectedSort + ANSI_RESET + " run [" + colors[Arrays.asList(*sorts)
                            .indexOf(selectedSort)] + (runCount) + ANSI_RESET + "]"
                    )
                    println(">   Sorting...")

                    // Start thread
                    val t = Thread(TimeManager(System.currentTimeMillis()))
                    t.start()
                    start = System.currentTimeMillis()

                    when (selectedSort) {
                        "Radix Sort" -> sorter.radixSort(items)
                        "Selection Sort" -> sorter.selectionSort(items)
                        "Quick Sort" -> sorter.quickSort(items)
                        "Merge Sort" -> sorter.mergeSort(items)
                    }
                    end = System.currentTimeMillis()
                    t.interrupt()

                    totalTime += end - start
                    runCount++
                }

                println()
                println()
                println("All sort runs completed.")
                println(
                    "Average sort time for " + colors[Arrays.asList(*sorts)
                        .indexOf(selectedSort)] + totalElements + ANSI_RESET + " elements: " + (totalTime / totalRuns).toDouble() + " ms"
                )

                println(
                    "Average sort time per element: " + ((totalTime / totalRuns).toDouble() / totalElements) + " ms"
                )

                // Complexity analysis printed here
                println(
                    colors[Arrays.asList(*sorts)
                        .indexOf(selectedSort)] + selectedSort + ANSI_RESET + " time complexity: " +
                            colors[Arrays.asList(*sorts)
                                .indexOf(selectedSort)] + complexityAnalysis(selectedSort) + ANSI_RESET
                )

                println()
                println("Press " + ANSI_PURPLE + "ENTER" + ANSI_RESET + " to restart program.")
                println("Type " + ANSI_RED + "EXIT" + ANSI_RESET + " to quit program.")
                input = `in`.nextLine()

                // Close program
                if ((input.lowercase(Locale.getDefault()) == "exit")) {
                    inputStage = 999
                } else {
                    input = ""
                    inputStage = 0
                }
            }
        }
    }

    fun sysClear() {
        print("\u001b[H\u001b[2J" + "\u001B[0m")
        System.out.flush()
    }

    fun generateRandomNumbers(size: Int): ArrayList<Int> {
        val nums = ArrayList<Int>()
        for (i in 0 until size) {
            nums.add(i)
        }
        Collections.shuffle(nums)
        return nums
    }

    @Throws(Exception::class)
    fun complexityAnalysis(selectedSort: String): String {
        var trialSizes = intArrayOf(10, 10000, 50000)
        val trialTimes = longArrayOf(0, 0, 0)
        var start: Long

        start = System.nanoTime() / 100
        for (i in trialSizes.indices) {
            var nums = generateRandomNumbers(trialSizes[i])
            when (selectedSort) {
                "Selection Sort" -> {
                    start = System.nanoTime() / 100
                    sorter.selectionSort(nums)
                }

                "Merge Sort" -> {
                    start = System.nanoTime() / 100
                    sorter.mergeSort(nums)
                }

                "Quick Sort" -> {
                    start = System.nanoTime() / 100
                    sorter.quickSort(nums)
                }

                "Radix Sort" -> {
                    start = System.nanoTime() / 100
                    sorter.radixSort(nums)
                }
            }
            val end = System.nanoTime() / 100
            trialTimes[i] = end - start
        }

        val analyzer: Analyzer = Analyzer(trialTimes, trialSizes[0], trialSizes[1], trialSizes[2])
        return analyzer.test()
    }



    @Throws(Exception::class)
    private fun isSorted(testMe: ArrayList<Int>) {
        //VALIDATE IF ARRAYLIST IS SORTED
        for (i in 1 until testMe.size) {
            if (testMe[i - 1] > testMe[i]) {
                throw Exception("ArrayList unsorted, index i-1 > index i : " + testMe[i - 1] + " > " + testMe[i])
            }
        }
    }

    // Modified Version
    private fun isUnsorted(testMe: ArrayList<Int?>): Int {
        //VALIDATE IF ARRAYLIST IS SORTED
        // Returns index of unsorted pair
        // If sorted, returns -1
        for (i in 1 until testMe.size) {
            if (testMe[i - 1]!! > (testMe[i])!!) {
                return i
            }
        }
        return -1
    }
}