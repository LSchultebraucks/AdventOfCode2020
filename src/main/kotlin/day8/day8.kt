import java.io.File

private fun readInput(): List<String> {
    return File("src/main/kotlin/day8/input_day8.txt").readLines()
}


fun main(args: Array<String>) {
    val bootCode = detectCycle(getBootCode())
    val validBootCode = brutForceProgramToTerminate(getBootCode())

    println("Part 1: ${bootCode.accumulator} Part 2: ${validBootCode.accumulator}")
}

fun getBootCode(): BootCode {
    val instructionList = ArrayList<Instruction>()

    for (entry in readInput()) {
        instructionList.add(parseStringToInstruction(entry))
    }

    return BootCode(instructionList.toTypedArray())
}


fun parseStringToInstruction(line: String): Instruction {
    val operation = line.substring(0, 3)
    var argument = Integer.parseInt(line.replace(Regex("[^0-9]"), ""))
    if (line.contains("-")) {
        argument *= -1
    }
    return Instruction(operation, argument)
}

fun detectCycle(bootCode: BootCode): BootCode {
    while (true) {
        if (bootCode.visitedInstructionAddresses.contains(bootCode.adress)) { // cycle condtion
            return bootCode
        }
        executeInstructionStep(bootCode)
    }
}

fun executeInstructionStep(bootCode: BootCode): BootCode {
    bootCode.visitedInstructionAddresses.add(bootCode.adress)

    when (bootCode.instructions[bootCode.adress].operation) {
        "acc" -> {
            bootCode.accumulator += bootCode.instructions[bootCode.adress].argument
            bootCode.adress++
        }
        "jmp" -> {
            bootCode.adress = bootCode.adress + bootCode.instructions[bootCode.adress].argument
        }
        "nop" -> {
            bootCode.adress++
        }
    }
    return bootCode
}

fun executeToEnd(bootCode: BootCode): BootCode {
    while (true) {
        executeInstructionStep(bootCode)
        if (bootCode.adress == bootCode.instructions.size) {
            return bootCode
        } else if (bootCode.visitedInstructionAddresses.contains(bootCode.adress)) {
            bootCode.accumulator = -1
            return bootCode
        }
    }
}

fun brutForceProgramToTerminate(bootCode: BootCode): BootCode {
    for ((index, inst) in bootCode.instructions.withIndex()) {
        val workingBootCode = getBootCode()
        if (inst.operation == "jmp") {
            workingBootCode.instructions[index].operation = "nop"
        } else if (inst.operation == "nop") {
            workingBootCode.instructions[index].operation = "jmp"
        }
        executeToEnd(workingBootCode)
        if (workingBootCode.accumulator != -1) {
            return workingBootCode
        }
    }

    return bootCode // no valid BootCode found
}

data class Instruction(
        var operation: String,
        val argument: Int
)

data class BootCode(
        val instructions: Array<Instruction>,
        var visitedInstructionAddresses: MutableList<Int> = mutableListOf(),
        var adress: Int = 0,
        var accumulator: Int = 0
)
