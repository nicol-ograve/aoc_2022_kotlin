package day14

import day14.model.Cave
import day14.model.printCaveTile
import utils.getDataLines
import utils.getDataScanner
import utils.printMatrix
import java.lang.Exception
import java.util.Scanner

fun main(args: Array<String>) {
    val lines = getDataLines(14, arrayOf("demso"))

    // TODO implemented when Up/Down logic were wrong. Must be checked again

    val input = readInput(lines)

    var finished = false
    val cave = Cave(input, { finished = true }, false)

    cave.print()

    while (!finished) {
        cave.moveSand()
    }

    cave.print()


    val result = cave.fallenSandUnits
    // 5156 too low

    println(result)
}