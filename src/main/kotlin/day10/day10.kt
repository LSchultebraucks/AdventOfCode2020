import java.io.File

private fun readInput(): List<Int> {
    return File("src/main/kotlin/day10/input_day10.txt").readLines().map{ it.toInt() }
}

fun main(args: Array<String>) {
    val part1: Int
    val part2: Long

    val adapters: List<Int> = readInput().sorted()
    val differencesCount = IntArray(4)
    differencesCount[adapters[0]]++
    differencesCount[3]++
    for (i in 1 until adapters.size) {
        differencesCount[adapters[i]-adapters[i-1]]++
    }

    part1 = differencesCount[1] * differencesCount[3]

    val dynamicProgramming = ArrayList<Long>(readInput().size + 2)
    val allAdapters = ArrayList<Int>(readInput().size + 2)
    allAdapters.add(0)
    allAdapters.addAll(adapters)
    allAdapters.add(allAdapters.last() + 3)

    for (index in allAdapters.indices) {
        when (index) {
            0, 1 -> dynamicProgramming.add(1)
            else -> {
                dynamicProgramming.add(
                        (if (allAdapters[index] - allAdapters[index - 1] < 4) dynamicProgramming[index - 1] else 0) +
                                (if (allAdapters[index] - allAdapters[index - 2] < 4) dynamicProgramming[index - 2] else 0) +
                                (if (index > 2 && allAdapters[index] - allAdapters[index - 3] < 4) dynamicProgramming[index - 3] else 0)
                )
            }
        }
    }

    part2 = dynamicProgramming.last()

    println("Part 1: $part1 Part 2: $part2")
}
