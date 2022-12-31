package day16.model

import java.util.LinkedList

class Logic(private val valves: Array<Valve>) {

    private val states = LinkedList<ScanState>()

    fun scan(initialValve: Valve): Int {

        var maxResult = -1
        val openableValvesCount = valves.filter { it.flowRate > 0 }.size

        val visitedSince = HashSet<Valve>()
        visitedSince.add(initialValve)

        val initialState = ScanState(
            0,
            listOf(initialValve, initialValve),
            26,
            0,
            emptySet(),
            emptyList(),
            listOf(visitedSince.toHashSet(), visitedSince.toHashSet()),
            HashMap()
        )

        states.addFirst(initialState)

        while (states.isNotEmpty()) {
            val state = states.pollLast()
            if (state.remainingMinutes > 0 && state.openedValves.size < openableValvesCount) {
                manageState(state)
            } else if (state.releasedPressure > maxResult) {
                maxResult = state.releasedPressure
                println("New max: $maxResult")
            }
        }

        return maxResult

    }

    private fun recursive() {

    }

    private fun manageState(state: ScanState) {
        val userIndex = state.nextUserTurn
        val currentValve = state.currentValve[userIndex]
        val openedValves = state.openedValves
        val decreaseMinute = userIndex == 0

        var actionsDone = false

        if (currentValve.flowRate > 0 && !openedValves.contains(currentValve)) {
            actionsDone = true
            states.addFirst(
                ReleasePressure(state, userIndex).execute(decreaseMinute)
            )
        }

        val arcs =
            if (state.crossedArcs.contains(currentValve)) state.crossedArcs[currentValve]!! else emptyList<Valve>()

        val arcsToCross = currentValve.reachableValves.filter {
            !arcs.contains(it) && !state.visitedSinceLastOpening[userIndex].contains(it)
        }

        arcsToCross
            .forEach {
                actionsDone = true
                states.addFirst(
                    CrossTunnel(state, it, userIndex).execute(decreaseMinute)
                )
            }

    }


    private fun printStatesHistory(state: ScanState) {

        println()
        println("== SOLUTION ==")
        println()

        state.actions.forEach {
            println(it)
            println()
        }
    }

}


