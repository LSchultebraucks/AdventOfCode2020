import java.io.File

private fun readInput(): List<String> {
    return File("src/main/kotlin/day7/input_day7.txt").readLines()
}


fun main(args: Array<String>) {
    var part1 = 0
    var part2 = 0

    val entries = readInput()

    val bags = mutableListOf<Bag>()

    for (entry in entries) {
        bags.add(parseEntry(entry))
    }

    for (bag in bags) {
        if (containsShinyGoldBag(bag.bagMap, bags)) {
            part1++
        }
    }

    val shinyGoldBag = findBagByType("shiny gold", bags)
    part2 = countBags(shinyGoldBag, bags)

    println("Part 1: $part1 Part 2: $part2")
}

fun parseEntry(desc: String): Bag {
    val type = desc.substringBefore(" contain").substringBefore(" bag")
    val bags = desc.substringAfter("contain ").removeSuffix(".").split(", ")
    var bagMap = mutableMapOf<String, Int>()
    for (bag in bags) {
        if (bag != "no other bags") {
            val cnt = Integer.parseInt(bag.find { it.isDigit() }.toString())
            val bagType = bag.substringAfter(("$cnt ")).substringBefore(" bag")
            bagMap[bagType] = cnt
        }
    }
    return Bag(type, bagMap)
}

fun containsShinyGoldBag(bagMap: Map<String, Int>, bags: List<Bag>): Boolean {
    val flattedBagTypes = mutableListOf<String>()
    flattedBagTypes.addAll(bagMap.keys)
    return if (flattedBagTypes.contains("shiny gold")) {
        true
    } else {
        containsShinyGoldBag(flattedBagTypes, bags)
    }
}

fun containsShinyGoldBag(flattedBagTypes: List<String>, bags: List<Bag>): Boolean {
    if (flattedBagTypes.isEmpty()) {
        return false
    }
    val newFlattedBagTypes = mutableListOf<String>()
    for (type in flattedBagTypes) {
        newFlattedBagTypes.addAll(findBagByType(type, bags).bagMap.keys)
    }
    return if (newFlattedBagTypes.contains("shiny gold")) {
        true
    } else {
        containsShinyGoldBag(newFlattedBagTypes, bags)
    }
}

fun findBagByType(type: String, bags: List<Bag>): Bag {
    for (bag in bags) {
        if (bag.type == type) {
            return bag
        }
    }
    return Bag("", emptyMap()) // should actually not occur - else input may be corrupted
}

fun countBags(bag: Bag, bags: List<Bag>): Int {
    var count = bag.bagMap.values.sum()
    for (bagEntry in bag.bagMap.entries) {
        val currentBag = findBagByType(bagEntry.key, bags)
        count+=  bagEntry.value * countBags(currentBag, bags)
    }
    return count
}

data class Bag(
        val type: String,
        var bagMap: Map<String, Int>
)
