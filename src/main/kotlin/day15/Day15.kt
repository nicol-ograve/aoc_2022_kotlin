package day15

import day15.model.MultiRange
import day15.model.Range
import day15.model.Sensor
import utils.getDataScanner
import java.util.Scanner

fun main(args: Array<String>) {
    val isDemo = false
    val scanner = getDataScanner(15, if (isDemo) arrayOf("demo") else emptyArray())
    part2(scanner, isDemo)

}

fun part2(scanner: Scanner, isDemo: Boolean) {

    val sensors = readSensorsInput(scanner).map {
        Sensor(it.first, it.second)
    }

    val rows = if (isDemo) 20 else 4000000
    val columns = rows

    for (y in 0..rows) {
        val totalRange = MultiRange(Range(0, columns))

        sensors.mapNotNull { it.beaconFreeRangeForY(y) }.forEach {
            totalRange.subtractRange(it)
        }

        if (totalRange.ranges.isNotEmpty()) {
            println("$y: ${totalRange.ranges}")
            println(totalRange.ranges.first().start * 4000000L + y)
        }
    }
}

fun part1(scanner: Scanner) {

    val scanY = 2000000

    val sensors = readSensorsInput(scanner).map {
        Sensor(it.first, it.second)
    }
    val yBeaconFreeRange = sensors.mapNotNull { it.beaconFreeRangeForY(scanY) }

    val yBeaconFreeArea = yBeaconFreeRange.fold(HashSet<Int>()) { set, range ->
        for (i in range.start..range.end) {
            set.add(i)
        }
        set
    }
    sensors.forEach {
        if (it.nearestBeaconLocation.y == scanY) {
            yBeaconFreeArea.remove(it.nearestBeaconLocation.x)
        }
    }



    println(yBeaconFreeArea.size)

}