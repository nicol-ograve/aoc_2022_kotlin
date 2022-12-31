 package day5

import day5.model.CargoCrane
import day5.utils.readInput
import utils.getDataScanner
        import java.util.Scanner

fun main(args: Array<String>) {

    val scanner = getDataScanner(5, args)

    val input = readInput(scanner)

    val crane = CargoCrane(input.stacks)
    crane.execute(input.commands)

    val result = crane.stacks.map { it.peek()!!.value }.joinToString { it.toString() }.replace(", ", "")

    println(result)
}