package day4

import utils.getDataScanner
import java.util.Scanner

fun main(args: Array<String>) {
    val scanner = getDataScanner(4, args)
    println(getOverlapsSum(scanner))


}
fun getOverlapsSum(scanner: Scanner): Int {
    var sum = 0

    while (scanner.hasNextLine()) {
        val line = scanner.nextLine()
        val ranges = line.split(",").map {
            val values = it.split("-")
            Range(values[0].toInt(), values[1].toInt())
        }

        if(ranges[0].overlapsCount(ranges[1]) > 0) {
            sum ++
        }


    }

    return sum
}

fun getContainedPairsSum(scanner: Scanner): Int {
    var sum = 0

    while (scanner.hasNextLine()) {
        val line = scanner.nextLine()
        val ranges = line.split(",").map {
            val values = it.split("-")
            Range(values[0].toInt(), values[1].toInt())
        }

        if (ranges[0].contains(ranges[1]) ||ranges[1].contains(ranges[0]))
        {
            sum++
        }


    }

    return sum
}