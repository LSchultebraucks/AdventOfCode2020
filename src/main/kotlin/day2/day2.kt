import java.io.File

private fun readInput(): List<String> {
    return File("src/main/kotlin/day2/input_day2.txt").readLines();
}

fun main(args: Array<String>) {
    var validPasswordCount = 0;
    var validPasswordCountNew = 0;

    var entries: List<String> = readInput();

    for (entry in entries) {
        val entryArgs = entry.split("-", " ", ":");
        val min = Integer.parseInt(entryArgs[0])
        val max = Integer.parseInt(entryArgs[1]);
        val char: Char = entryArgs[2].single();
        val word = entryArgs[4];
        val occurencesCount = word.filter { it == char }.count();
        if (occurencesCount in min..max) {
            validPasswordCount++;
        }
        if ((word[min-1] == char && word[max-1] != char) || (word[min-1] != char && word[max-1] == char)) {
            validPasswordCountNew++;
        }
    }

    println("Part 1: $validPasswordCount Part 2: $validPasswordCountNew");
}

