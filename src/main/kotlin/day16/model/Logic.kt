package day16.model

import java.util.LinkedList
import kotlin.properties.Delegates

class Logic(private val valves: Array<Valve>) {

    private var openableValvesCount by Delegates.notNull<Int>()
    private var state by Delegates.notNull<ScanState>()

    private val states = LinkedList<ScanState>()

    fun scan(initialValve: Valve): Int {

        openableValvesCount = valves.filter { it.flowRate > 0 }.size

        val visitedSince = HashSet<Valve>()
        visitedSince.add(initialValve)

        val initialState = ScanState(
            0,
            mutableListOf(initialValve, initialValve),
            26,
            0,
            mutableSetOf(),
            mutableListOf(),
            mutableListOf(visitedSince.toHashSet(), visitedSince.toHashSet()),
            HashMap(),
            Array(2) { 0 }
        )

        valves.forEach {
            initialState.crossedArcs.putIfAbsent(it, HashSet())
        }

        state = initialState
        states.addFirst(initialState)

        val result = recursive()

        println("Completed")
        return result
    }


    private fun recursive(): Int {

        val sameValve =  state.nextUserTurn == 0 && state.currentValve[0] == state.currentValve[1]

        if (state.remainingMinutes <= 0 || state.openedValves.size == openableValvesCount) {
            return state.releasedPressure
        }

        val actions = mutableListOf<ScanAction>()
        val actionsToRevert = mutableListOf<ScanAction>()

//        val state2 = state.copy()

        while (state.crossingTunnelSteps[state.nextUserTurn] > 0) {
            val action = KeepTunnelCrossing(state)
            action.execute()
            actionsToRevert.add(action)
        }

        val currentValve = state.currentValve[state.nextUserTurn]

        if (currentValve.flowRate > 0 && !state.openedValves.contains(currentValve)) {
            actions.add(ReleasePressure(state))
        }

        val arcs = state.crossedArcs[currentValve]!!

        currentValve.reachableValves.keys.filter {
            !arcs.contains(it) && !state.visitedSinceLastOpening[state.nextUserTurn].contains(it)
        }.filter {
            state.maxId == null || state.nextUserTurn != 1 || it.id <= state.maxId!!
        }
            .forEach {
            val cost = currentValve.reachableValves[it]!!
            if (state.remainingMinutes >= cost)
                actions.add(CrossTunnel(state, it, currentValve.reachableValves[it]!!, sameValve))
        }



        val result = if (actions.isEmpty()) state.releasedPressure else actions.maxOf {
            it.execute()
            val result = recursive()
            it.undo()
            result
        }

        actionsToRevert.forEach { it.undo() }

//        if (state2 != state) {
//            throw RuntimeException("WRONG state")
//        }

        return result

    }

    private fun manageState(state: ScanState) {
        val userIndex = state.nextUserTurn
        val currentValve = state.currentValve[userIndex]
        val openedValves = state.openedValves
        val decreaseMinute = userIndex == 0

        var actionsDone = false

        if (currentValve.flowRate > 0 && !openedValves.contains(currentValve)) {
            actionsDone = true
//            states.addFirst(
//                ReleasePressure(state, userIndex).execute()
//            )
        }

        val arcs =
            if (state.crossedArcs.contains(currentValve)) state.crossedArcs[currentValve]!! else emptyList<Valve>()

        val arcsToCross = currentValve.reachableValves.keys.filter {
            !arcs.contains(it) && !state.visitedSinceLastOpening[userIndex].contains(it)
        }

        arcsToCross
            .forEach {
                actionsDone = true
//                states.addFirst(
//                    CrossTunnel(state, it, userIndex).execute(decreaseMinute)
//                )
            }

    }


    private var printed = false
    private fun printStatesHistory(state: ScanState) {

        if (!printed) {
            printed = true
            var i = 0
            state.actions.forEach {
                if (i % 2 == 0) {
                    println()
                    println("== Minute ${i / 2 + 1} ==")

                }
                println(it)
                i++
            }
        }
    }

}


