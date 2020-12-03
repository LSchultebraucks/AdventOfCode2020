import java.io.File

private fun readInput(): List<String> {
    return File("src/main/kotlin/day3/input_day3.txt").readLines()
}


fun main(args: Array<String>) {
    var part1 = 0
    var part2 = 1L

    part1 = findTrees(3, 1)

    var treeCounts: List<Int> = listOf(
            findTrees(1,1), findTrees(3, 1),
            findTrees(5, 1), findTrees(7, 1),
            findTrees(1, 2))

    for (d in treeCounts) {
        part2 *= d
    }

    println("Part 1: $part1 Part 2: $part2")
}

private fun findTrees(x_delta: Int, y_delta: Int): Int {
    var entries: List<String> = readInput()

    var treeCount = 0

    val maxHeight = entries.size
    val maxWidth = entries[0].length;

    var x = 0
    for (y in 0 until maxHeight step y_delta) {
        if (entries[y][x] == '#') {
            treeCount++
        }
        x += x_delta
        x %= maxWidth
    }
    return treeCount
}
