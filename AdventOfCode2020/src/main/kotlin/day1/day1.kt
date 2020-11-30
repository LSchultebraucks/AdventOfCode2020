import java.io.File


private fun readInput(): List<String> {
    return File("input_day1.txt").readLines();
}

fun main(args: Array<String>) {
    var part1: Int = -1;
    var part2: Int = -1;

    var a: Int = 0;

    for (element in readInput()) {
        println(element)
        a++;
    }

    println("Part 1: $part1 Part 2: $part2 $a");
}

