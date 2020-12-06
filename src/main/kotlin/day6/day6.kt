import java.io.File
import java.lang.StringBuilder
import kotlin.collections.Map as Map

private fun readInput(): List<String> {
    return File("src/main/kotlin/day6/input_day6.txt").readLines()
}


fun main(args: Array<String>) {
    var part1 = 0
    var part2 = 0

    val entries = readInput()


    val sb = StringBuilder()
    for (entry in entries) {
        if (entry.isNotEmpty()) {
            sb.append(entry)
        } else {
            part1 += countAnsweredQuestions(getAnsweredQuestionMap(sb.toString()))
            sb.setLength(0) // clears string builder
        }
    }
   part1 += countAnsweredQuestions(getAnsweredQuestionMap(sb.toString()))


    var currentAnswers = mutableListOf<Map<Char, Int>>()
    for (entry in entries) {
        if (entry.isNotEmpty()) {
            currentAnswers.add(getAnsweredQuestionMap(entry))
        } else { // new line - end of group
            part2 += getEveryoneAnsweredQuestionCount(currentAnswers)
            currentAnswers.clear()
        }
    }
    part2 += getEveryoneAnsweredQuestionCount(currentAnswers)

    println("Part 1: $part1 Part 2: $part2")
}

fun getAnsweredQuestionMap(group: String): Map<Char, Int> {
    return group.groupingBy { it }.eachCount()
}

fun countAnsweredQuestions(group: Map<Char, Int>): Int {
    return group.size
}

fun getEveryoneAnsweredQuestionCount(groupAnswers: List<Map<Char, Int>>): Int {
    var cnt = 0
    val alphabet = "abcdefghijklmnopqrstuvwxyz"
    for (letter in alphabet) {
        var containsLetterCnt = 0
        for (group in groupAnswers) {
            if (group.containsKey(letter) && group[letter]!! > 0) {
                containsLetterCnt++
            }
        }
        if (containsLetterCnt == groupAnswers.size) {
            cnt++
        }
    }
    return cnt
}
