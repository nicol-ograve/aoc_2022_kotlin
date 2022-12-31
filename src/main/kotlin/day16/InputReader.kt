package day16

import day16.model.Valve
import shared.Point
import java.util.Scanner

val regex = Regex("Valve (\\w+) has flow rate=(\\d+); tunnels? leads? to valves? (.+)")

fun readValvesInput(lines: List<String>): Array<Valve> {

    val valvesMap = hashMapOf<String, Valve>()
    val reachableIdsMap = hashMapOf<String, List<String>>()


    for (line in lines) {
        val matches = regex.find(line)
        val (id, rate, neighbors) = matches!!.destructured

        val valve = Valve(id, rate.toInt())

        valvesMap[id] = valve
        reachableIdsMap[id] = neighbors.split(",").map { it.trim() }

    }

    for (id in valvesMap.keys){
        val neighbors = reachableIdsMap[id]!!.map { valvesMap[it]!! }
        valvesMap[id]!!.addReachableValves(neighbors)
    }

    return valvesMap.values.toTypedArray()
}