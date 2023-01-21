package day16

import day16.model.Valve
import java.util.LinkedList
import kotlin.math.pow


data class PathNode(val valve: Valve, val arcCost: Int, val isOpened: Boolean) {
    override fun toString(): String {
        return valve.toString()
    }
}

data class Path(var valves: List<PathNode>, var cost: Int, val pressureReleased: Int) {
    val costRatio = pressureReleased.toDouble() / cost.toDouble()

    fun addNode(node: PathNode) {
        valves = valves + node
        cost += node.arcCost
    }

}

class PathFinder(val valves: Array<Valve>) {

    val minPaths: Array<Array<Int?>> = Array(valves.size) { Array(valves.size) { null } }

    fun findPath(initialValve: Valve, remainingMinutes: Int): Path? {

        val list = LinkedList<Valve>()
        val bestPathsMap = HashMap<Valve, Path>()

        list.add(initialValve)
        bestPathsMap[initialValve] = Path(listOf(PathNode(initialValve, 0, true)), 0, 0)

        while (list.isNotEmpty()) {
            val valve = list.pollFirst()
            val path = bestPathsMap[valve]!!

            val neighbors = valve.reachableValves.filter { it.key != initialValve }
            neighbors.forEach {
                val neighborPath = bestPathsMap[it.key]

                val newPathCost = path.cost + it.value + 1

                if (newPathCost < remainingMinutes) {
                    val newPathPressure =
                        if (!it.key.opened) path.pressureReleased + (remainingMinutes - path.cost - 1) * it.key.flowRate
                        else path.pressureReleased
                    val node = PathNode(it.key, it.value, it.key.opened)
                    val newPath = Path(path.valves + node, newPathCost, newPathPressure)

                    if (neighborPath == null
                        || neighborPath.pressureReleased == 0
                        || newPath.cost < neighborPath.cost
                    ) {

                        bestPathsMap[it.key] = newPath
                        if (!list.contains(it.key)) {
                            list.add(it.key)
                        }
                    }
                }
            }

        }

        bestPathsMap.keys.forEach {
            minPaths[initialValve.index][it.index] = bestPathsMap[it]!!.cost
        }


        val paths =
            bestPathsMap.values.filter { it.pressureReleased > 0 }
                .map { checkBestCombination(it, remainingMinutes) }


        val bestPath = paths.maxBy { it.valves.last().valve.flowRate / it.cost }
//    return if (paths.isEmpty()) null else paths.sortedWith(comparator).first()

        return bestPath
    }

    fun calculateMinCosts(initialValve: Valve) {
        val list = LinkedList<Valve>()
        val minCostsMap = HashMap<Valve, Int>()

        list.add(initialValve)
        minCostsMap[initialValve] = 0

        while (list.isNotEmpty()) {
            val valve = list.pollFirst()
            val valveCost = minCostsMap[valve]!!

            val neighbors = valve.reachableValves

            neighbors.keys.forEach {
                val neighborCost = neighbors[it]!! + valveCost
                if(minCostsMap[it] == null || minCostsMap[it]!! > neighborCost) {
                    minCostsMap[it] = neighborCost
                    list.add(it)
                }
            }


        }

        minCostsMap.keys.forEach {
            minPaths[initialValve.index][it.index] = minCostsMap[it]!!
        }

    }

    fun getBestPathsFrom(path: Path): List<Path> {
        val bestPathsMap = HashMap<Valve, Path>()
        throw RuntimeException()
    }

    private fun checkBestCombination(path: Path, remainingMinutes: Int): Path {


        val combinations = ArrayList<Path>()
        combinations.add(Path(listOf(path.valves[0]), 0, 0))

        path.valves.subList(1, path.valves.size).forEach { node ->
            if (!node.valve.opened) {

                val combs = ArrayList<Path>()
                combs.addAll(combinations)
                combinations.clear()

                combs.forEach {

                    // Closed valve
                    combinations.add(
                        Path(
                            it.valves + node.copy(isOpened = false),
                            it.cost + node.arcCost,
                            it.pressureReleased
                        )
                    )

                    // Opened valve
                    val openedCaseCost = it.cost + node.arcCost + 1
                    combinations.add(
                        Path(
                            it.valves + node.copy(isOpened = true),
                            openedCaseCost,
                            it.pressureReleased + node.valve.flowRate * (remainingMinutes - openedCaseCost)
                        )
                    )
                }
            } else {
                combinations.forEach {
                    it.addNode(node)
                }
            }
        }
        return combinations.maxBy { it.pressureReleased }

    }
}

fun Int.to2ByteArray(): ByteArray = byteArrayOf(toByte(), shr(8).toByte())