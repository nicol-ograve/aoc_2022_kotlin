package day16

import day12.path_finder.PathFinder
import day16.model.Logic
import day16.model.Valve
import utils.getDataLines

lateinit var minPaths: Array<Array<Int>>

fun main(args: Array<String>) {
    val isDemo = true
    val lines = getDataLines(16, if (isDemo) arrayOf("dexmo") else emptyArray())

    val valves = readValvesInput(lines)
    valves.forEachIndexed { index, valve -> valve.index = index }
    val initialValve = valves.first { it.id == "AA" }

    val startTime = System.currentTimeMillis()

    val pathFinder = PathFinder(valves)

    valves.forEach {
        pathFinder.calculateMinCosts(it)
    }

//    pathLonely.add(initialValve)
    pathHE.first.add(initialValve)
    pathHE.second.add(initialValve)

    minPaths = pathFinder.minPaths.map { it.map { it!! }.toTypedArray() }.toTypedArray()

    valves.filter { it != initialValve }.forEach { remaining.add(it) }

    recHuman(Pair(26, 26), 0)

//    val result = trip(valves, initialValve, 30)
//    val result = Logic(valves).scan(initialValve)
    val endTime = System.currentTimeMillis()

    val duration = endTime - startTime
    val durationSeconds = duration / 1000
    val durationMillis = duration % 1000
    println("Duration: $durationSeconds.$durationMillis")

    println(maxPressure)
}

val pathLonely = ArrayList<Valve>()

val remaining = ArrayList<Valve>()

val pathHE = Pair(ArrayList<Valve>(), ArrayList<Valve>())

var maxPressure = 0


fun rec(remainingMinutes: Int, pressure: Int) {
    if (pressure > maxPressure) {
        maxPressure = pressure
    }

    val size = remaining.size
    val previous: Valve = pathLonely.last()
    for (i in 0 until size) {

        val valve: Valve = remaining.removeAt(i)
        val arcCost = minPaths[previous.index][valve.index]
        if (remainingMinutes > arcCost + 1) {
            val newRemainingMinutes = remainingMinutes - arcCost - 1
            val newPressure = valve.flowRate * newRemainingMinutes
            pathLonely.add(valve)

            rec(newRemainingMinutes, pressure + newPressure)

            pathLonely.remove(valve)
        }
        remaining.add(i, valve)
    }

}

fun recHuman(remainingMinutes: Pair<Int, Int>, pressure: Int) {
    if (pressure > maxPressure) {
        maxPressure = pressure
    }

    val size = remaining.size
    val previous: Valve = pathHE.first.last()
    for (i in 0 until size) {

        val valve: Valve = remaining.removeAt(i)
        val arcCost = minPaths[previous.index][valve.index]
        if (remainingMinutes.first > arcCost + 1) {
            val newRemainingMinutes = Pair(remainingMinutes.first - arcCost - 1, remainingMinutes.second)
            val newPressure = valve.flowRate * newRemainingMinutes.first
            pathHE.first.add(valve)

            recElephant(newRemainingMinutes, pressure + newPressure, i)

            pathHE.first.remove(valve)
        }
        remaining.add(i, valve)
    }

}

fun recElephant(remainingMinutes: Pair<Int, Int>, pressure: Int, index: Int) {
    if (pressure > maxPressure) {
        maxPressure = pressure
    }

    val size = remaining.size
    val previous: Valve = pathHE.second.last()
    val humanPrevious = pathHE.first[pathHE.first.size - 2]
    val humanCurrent = pathHE.first.last()

    val sameValve = humanPrevious == previous

    for (i in 0 until size) {

        val valve: Valve = remaining.removeAt(i)
        val arcCost = minPaths[previous.index][valve.index]
        if (remainingMinutes.second > arcCost + 1 && (!sameValve || humanCurrent.index <= valve.index)) {
            val newRemainingMinutes = Pair(remainingMinutes.first, remainingMinutes.second - arcCost - 1)
            val newPressure = valve.flowRate * newRemainingMinutes.second
            pathHE.second.add(valve)

            recHuman(newRemainingMinutes, pressure + newPressure)

            pathHE.second.remove(valve)
        }
        remaining.add(i, valve)
    }

}


//)un trip(valves: Array<Valve>, initialValve: Valve, initialMinutes: Int): Int {
//
//
//    var totalPressure = 0
//    var minutes = initialMinutes
//    initialValve.opened = true
//
//    val pathComparator: Comparator<Path> = Comparator { l, r -> l.cost.compareTo(r.cost) }
//
//    var valve = initialValve
//    while (initialMinutes > 0 && valves.any { !it.opened }) {
//        val path = findPath(valve, minutes, pathComparator) ?: return totalPressure
//
//
//        totalPressure += path.pressureReleased
//        minutes -= path.cost
//        path.valves.filter { it.isOpened }.forEach { openedValve ->
//            valves.single { it.id == openedValve.valve.id }.opened = true
//        }
//
//        println("PATH")
//        println("OpenedValves: ${path.valves.filter { !it.valve.opened }.joinToString { it.valve.id }}")
//        println("Minute: ${30 - minutes}")
//        println("Releasing: ${path.pressureReleased}")
//        println("Total:: $totalPressure")
//        println()
//
//        valve = valves.find { it.id == path.valves.last().valve.id }!!
//    }
//
//    return totalPressure
//}
