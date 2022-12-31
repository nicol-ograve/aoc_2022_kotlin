package day5.utils

import day5.model.Command
import day5.model.CargoStack
import day5.model.Crate
import java.util.*

data class Input(val stacks: Array<CargoStack>, val commands: Stack<Command>) {}

fun readInput(scanner: Scanner): Input {
    val crateLines = Stack<String>()
    lateinit var cargoStacks: Array<CargoStack>
    val commands = Stack<Command>()

    var isScanningCrates = true

    do {

        val line = scanner.nextLine()

        if (isStacksListLine(line)) {
            cargoStacks = initCargoStacks(line)
            isScanningCrates = false
        } else {
            crateLines.push(line)
        }

    } while (isScanningCrates)

    crateLines.reverse()
    crateLines.forEach { crateLine ->
        for (i in cargoStacks.indices) {
            val valueIndex = i * 4 + 1
            if(valueIndex < crateLine.length) {
                val crateValue = crateLine[valueIndex]
                if (crateValue != ' ') {
                    cargoStacks[i].push(Crate(crateValue))
                }
            }

        }
    }

    // Jump white line
    scanner.nextLine()

    while (scanner.hasNextLine()){
        commands.push(commandFromLine(scanner.nextLine()))
    }

    return Input(cargoStacks, commands)

}

val isStacksListLine = { line: String -> line.startsWith(" 1") }

val initCargoStacks = { cargoListLine: String,  -> cargoListLine.split("   ").map { CargoStack() }.toTypedArray() }

val commandFromLine = {line: String ->
    val countEnd = line.indexOf(' ', 5)
    val count = line.substring(5, countEnd).toInt()

    val fromEnd = line.indexOf(' ', countEnd + 6)
    val from = line.substring(countEnd + 6, fromEnd).toInt()

    val to = line.substring(fromEnd + 4).toInt()

    Command(count, from, to)

}