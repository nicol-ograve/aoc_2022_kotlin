package day11

import day11.logic.Dispatcher
import utils.getDataScanner

fun main(args: Array<String>) {
    execute(true)
    execute(false)
}


fun execute(isDemo: Boolean) {
    val scanner = getDataScanner(11, if (isDemo) arrayOf("demo") else emptyArray())
    val monkeys = readInput(scanner)
    Dispatcher.monkeys = monkeys

    for (i in 0 until 20) {
        monkeys.forEach { it.runTurn() }
    }

    monkeys.sortBy { it.inspections * -1L }

    val result = monkeys[0].inspections * monkeys[1].inspections;

    Dispatcher.itemsList.keys.forEach {
        println("Item ${it.index}: ")
        Dispatcher.itemsList[it]?.forEach {
            println(it)
        }
    }

    if (isDemo) {
        print("Demo: ")
    }
    println(result)
}
