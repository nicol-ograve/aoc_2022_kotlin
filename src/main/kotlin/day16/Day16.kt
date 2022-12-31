package day16

import day16.model.Logic
import utils.getDataLines
import utils.getDataScanner
import java.util.Scanner

fun main(args: Array<String>) {
    val isDemo = true
    val lines = getDataLines(16, if (isDemo) arrayOf("demfo") else emptyArray())

    val valves = readValvesInput(lines)
    val initialValve = valves.first { it.id == "AA" }

    val startTime = System.currentTimeMillis()
    val result = Logic(valves).scan(initialValve)
    val endTime = System.currentTimeMillis()

    val duration = endTime - startTime
    val durationSeconds = duration / 1000
    val durationMillis = duration % 1000
    println("Duration: $durationSeconds.$durationMillis")

    println(result)
}