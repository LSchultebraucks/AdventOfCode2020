import java.io.File

private fun readInput(): List<String> {
    return File("src/main/kotlin/day5/input_day5.txt").readLines()
}


fun main(args: Array<String>) {
    var part1 = 0
    var part2 = 0

    var entries: List<String> = readInput()


    var seats = mutableListOf<Seat>();

    for (entry in entries) {
        val rowId = binaryPartitioning(entry.substring(0, 7), 0, 127, 'F', 'B')
        val columnId = binaryPartitioning(entry.substring(7, 10), 0, 7, 'L', 'R')
        val seat = Seat(rowId, columnId)
        if (seat.Id() > part1) {
            part1 = seat.Id()
        }
        seats.add(seat)
    }

    seats = seats.sortedWith(compareBy(Seat::rowId, Seat::columnId)).toMutableList()

    val lowestRowId = seats[0].rowId
    val highestRowId = seats[seats.size - 1].rowId
    var currentColumn = 0

    for (seat in seats) {
        if (seat.rowId != lowestRowId && seat.rowId != highestRowId) {
            if (seat.columnId == currentColumn) {
                if (seat.columnId != 7) {
                    currentColumn++
                } else {
                    currentColumn = 0
                }
            } else {
                part2 = seat.rowId * 8 + currentColumn;
            }
        }
    }

    println("Part 1: $part1 Part 2: $part2")
}

fun binaryPartitioning(word: String, left: Int, right: Int, lowerChar: Char, upperChar: Char): Int {
    if (left == right) { // word should be empty then
        return left
    } else {
        val char = word[0]
        val newWord = word.substring(1)
        if (char == lowerChar) {
            return binaryPartitioning(newWord, left, (right + left) / 2, lowerChar, upperChar)
        } else if (char == upperChar) {
            return binaryPartitioning(newWord, (left + right + 1) / 2, right, lowerChar, upperChar)
        }
    }
    return -1 // error
}

data class Seat(
        var rowId: Int,
        var columnId: Int
) {
    fun Id(): Int {
        return rowId * 8 + columnId
    }
}
