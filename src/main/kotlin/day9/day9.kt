import java.io.File

private fun readInput(): LongArray {
    return File("src/main/kotlin/day9/input_day9.txt").readLines().map{ it.toLong() }.toLongArray()
}


fun main(args: Array<String>) {
    val part1: Long
    val part2: Long

    val numbers: LongArray = readInput()

    part1 = findNumberWithoutSumProperty(numbers)

    part2 = findEncryptionWeakness(numbers, part1)

    println("Part 1: $part1 Part 2: $part2")
}

fun findNumberWithoutSumProperty(numbers: LongArray): Long {
    val preambleSize = 25
    for (index in preambleSize..numbers.size) {
        if (!hasProperty(numbers, index, preambleSize)) {
            return numbers[index]
        }
    }
    return -1 // error
}

fun hasProperty(numbers: LongArray, index: Int, preambleSize: Int): Boolean {
    for (i in index-preambleSize..index) {
        for (j in index-preambleSize..index) {
            if (i != j && numbers[i] + numbers[j] == numbers[index]) {
                return true
            }
        }
    }
    return false
}

fun findEncryptionWeakness(numbers: LongArray, target: Long): Long {
    for (i in numbers.indices) {
        val potentialEncryptionWeaknessList = mutableListOf<Long>()
        for (j in i until numbers.size) {
            potentialEncryptionWeaknessList.add(numbers[j])
            if (potentialEncryptionWeaknessList.size >= 2 && potentialEncryptionWeaknessList.sum() == target) {
                return potentialEncryptionWeaknessList.minOf { it } + potentialEncryptionWeaknessList.maxOf { it }
            }
        }
    }
    return -1
}
