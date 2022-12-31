package day13

import day13.model.ListPacket
import utils.getDataLines
import utils.getDataScanner

fun main(args: Array<String>) {
    part2()
}

fun part2() {
    val packets = getDataLines(13, arrayOf("demjo")).filter { it.isNotEmpty() }.map {
        ListPacket(it)
    } + ListPacket("[[2]]") + ListPacket("[[6]]")

    val result = packets.sortedWith(comparator = { l, r ->
        val result = arePacketsInRightOrder(l, r)
        when (result) {
            true -> -1
            false -> 1
            else -> 0
        }

    })
        .mapIndexed { index, listPacket -> if (listPacket.value == "[[2]]" || listPacket.value == "[[6]]") index + 1 else null }
        .filterNotNull()
        .reduce { first, second -> first * second }

    println(result)


}

fun part1() {
    val scanner = getDataScanner(13, arrayOf("demo"))

    val pairs = readPacketsInput(scanner)


    val result =
        pairs.mapIndexed { index, pair ->
            println()
            println("== Pair ${index + 1} ==")
            val rightOrder = arePacketsInRightOrder(pair.first, pair.second)
            if (rightOrder == null || rightOrder) {
                println("Index: ${index + 1}")
                index + 1
            } else 0

        }.sum()

    println(result)
}
