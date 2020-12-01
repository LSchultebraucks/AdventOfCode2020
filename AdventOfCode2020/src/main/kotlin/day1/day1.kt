import java.io.File

private fun readInput(): IntArray {
    val strings: Array<String> = File("input_day1.txt").readLines().toTypedArray();
    val ints = IntArray(strings.size);
    strings.indices.forEach { ints[it] = strings[it].toInt() }
    return ints;
}

fun main(args: Array<String>) {
    var part1: Int = -1;
    var part2: Int = -1;

    var numbers: IntArray = readInput();

    for (a1 in numbers) for (a2 in numbers) if (a1 + a2 == 2020) {
        part1 = a1 * a2;
    }

    for (a1 in numbers) for (a2 in numbers) for (a3 in numbers) if (a1 + a2 + a3 == 2020) {
        part2 = a1 * a2 * a3;
    }

    println("Part 1: $part1 Part 2: $part2");
}

