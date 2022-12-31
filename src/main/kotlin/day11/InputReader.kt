package day11

import day11.model.Item
import day11.model.Monkey
import day11.logic.MonkeyAction
import day11.logic.MonkeyOperation
import java.util.Scanner

fun readInput(scanner: Scanner): Array<Monkey> {

    val monkeys = mutableListOf<Monkey>()

    while (scanner.hasNextLine()) {
        monkeys.add(readMonkey(scanner, monkeys.size))
    }

    return monkeys.toTypedArray()
}

fun readMonkey(scanner: Scanner, index: Int): Monkey {

    // Header
    val empty = scanner.nextLine().isEmpty()
    if(empty){
        scanner.nextLine()
    }

    val items: Array<Item> =
        scanner.nextLine().replace("Starting items: ", "").trim().split(", ").map { Item(it.toLong()) }.toTypedArray()

    val operation = parseOperation(scanner.nextLine().replace("Operation: new =", "").trim())


    val divisibleBy = scanner.nextLine().replace("Test: divisible by ", "").trim().toLong()
    val positiveDestination = scanner.nextLine().replace("If true: throw to monkey ", "").trim().toInt()
    val negativeDestination = scanner.nextLine().replace("If false: throw to monkey ", "").trim().toInt()
    val action = MonkeyAction(
        divisibleBy,
        destinationIndexIfTrue = positiveDestination,
        destinationIndexIfFalse = negativeDestination
    )

    return Monkey(index, items, operation, action)
}

fun parseOperation(operation: String): MonkeyOperation {
    val parts = operation.split(" ")
    return MonkeyOperation(
       if (parts[2] == "old") null else parts[2].toLong(),
        parts[1]
    )
}


