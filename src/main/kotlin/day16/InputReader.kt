package day16

import day16.model.Valve

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

    for (id in valvesMap.keys) {
        reachableIdsMap[id]!!.map { valvesMap[it]!! }.forEach {
            valvesMap[id]!!.addNeighbor(it)
        }
    }

    valvesMap.values.filter { it.id != "AA" && it.flowRate == 0 }.forEach { valve ->
        replaceValveFromNeighbors(valve)
    }

    return valvesMap.values.filter { it.id == "AA" || it.flowRate > 0 }.toTypedArray()
}

fun replaceValveFromNeighbors(valve: Valve) {
    val neighbors = valve.reachableValves.keys

    neighbors.forEach { neighbor ->
        neighbor.reachableValves.remove(valve)

        neighbors.filter { it != neighbor }.forEach {
            val currentCost = neighbor.reachableValves[it]
            val newCost = valve.reachableValves[neighbor]!! + valve.reachableValves[it]!!
            if (currentCost == null || currentCost > newCost) {
                neighbor.reachableValves[it] = newCost
            }
        }
    }
}