import java.io.File
import java.lang.StringBuilder

private fun readInput(): List<String> {
    return File("src/main/kotlin/day4/input_day4.txt").readLines()
}

fun main(args: Array<String>) {
    var part1 = 0
    var part2 = 0

    var entries: List<String> = readInput()
    var passports = mutableListOf<Passport>();
    val sb = StringBuilder()

    for (entry in entries) {
        if (entry.isNotEmpty()) {
            sb.append("$entry ")
        } else {
            passports.add(parseInput(sb.toString()))
            sb.setLength(0) // clears string builder
        }
    }
    passports.add(parseInput(sb.toString()))

    for (passport in passports) {
        if (isValid(passport)) {
            part1++
            if (isValidPartTwo(passport)) {
                part2++
            }
        }
    }

    println("Part 1: $part1 Part 2: $part2")
}

fun isValidPartTwo(passport: Passport): Boolean {
    val isByrValid = Integer.parseInt(passport.byr) in 1920..2002
    val isIyrValid = Integer.parseInt(passport.iyr) in 2010..2020
    val isEyrValid = Integer.parseInt(passport.eyr) in 2020..2030
    var isHgtValid = false
    val heightUnit = passport.hgt.substring(passport.hgt.length - 2, passport.hgt.length)
    if (heightUnit == "cm") {
        val height = Integer.parseInt(passport.hgt.substringBefore("cm"))
        isHgtValid = height in 150..193
    } else if (heightUnit == "in") {
        val height = Integer.parseInt(passport.hgt.substringBefore("in"))
        isHgtValid = height in 59..76
    }

    val isHclValid = passport.hcl.matches(Regex("#[0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f]"))
    val isEclValid = passport.ecl in listOf<String>("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    val isPidValid = passport.pid.length == 9 // test if is int
    return isByrValid && isIyrValid && isEyrValid && isHgtValid && isHclValid && isEclValid && isPidValid
}

fun isValid(passport: Passport): Boolean {
    return passport.byr.isNotEmpty() && passport.iyr.isNotEmpty() && passport.eyr.isNotEmpty() && passport.hgt.isNotEmpty()
            && passport.hcl.isNotEmpty() && passport.ecl.isNotEmpty() && passport.pid.isNotEmpty()
}

fun parseInput(input: String): Passport {
    return Passport(
            input.substringAfter("byr:", "").substringBefore(" ", ""),
            input.substringAfter("iyr:", "").substringBefore(" ", ""),
            input.substringAfter("eyr:", "").substringBefore(" ", ""),
            input.substringAfter("hgt:", "").substringBefore(" ", ""),
            input.substringAfter("hcl:", "").substringBefore(" ", ""),
            input.substringAfter("ecl:", "").substringBefore(" ", ""),
            input.substringAfter("pid:", "").substringBefore(" ", ""),
            input.substringAfter("cid:", "").substringBefore(" ", ""))
}

data class Passport(
        var byr: String,
        var iyr: String,
        var eyr: String,
        var hgt: String,
        var hcl: String,
        var ecl: String,
        var pid: String,
        var cid: String
)
