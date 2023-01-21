package day16.model

val logActions = false

sealed class ScanAction(val state: ScanState) {
    abstract fun execute()
    abstract fun undo()

    protected val userIndex: Int
        get() = state.nextUserTurn


    protected val decreaseMinute: Boolean = userIndex == 0
}

class ReleasePressure(state: ScanState) : ScanAction(state) {
    private var cachedValves: MutableSet<Valve>? = null

    private var value =
        state.currentValve[userIndex].flowRate * (if (decreaseMinute) state.remainingMinutes - 1 else state.remainingMinutes)
    private val str = "${userIndex}) Releasing Pressure to ${state.currentValve[userIndex]} ($value)"
    override fun toString(): String {
        return str
    }

    override fun execute() {
        val valve = state.currentValve[userIndex]
        val remainingMinutes = if (decreaseMinute) state.remainingMinutes - 1 else state.remainingMinutes

        state.remainingMinutes = remainingMinutes
        state.releasedPressure += remainingMinutes * valve.flowRate
        state.openedValves.add(valve)
        if (logActions) {
            state.actions.add(this)
        }

        cachedValves = state.visitedSinceLastOpening[userIndex]
        state.visitedSinceLastOpening[userIndex] = HashSet<Valve>()

        state.nextUserTurn = if (userIndex == 0) 1 else 0
    }

    override fun undo() {

        state.nextUserTurn = if (userIndex == 0) 1 else 0

        val valve = state.currentValve[userIndex]
        val remainingMinutes = if (decreaseMinute) state.remainingMinutes + 1 else state.remainingMinutes

        state.releasedPressure -= state.remainingMinutes * valve.flowRate
        state.remainingMinutes = remainingMinutes
        state.openedValves.remove(valve)

        if (logActions) {
            state.actions.remove(this)
        }
        state.visitedSinceLastOpening[userIndex] = cachedValves!!

    }

}

class CrossTunnel(state: ScanState, val newValve: Valve, val cost: Int, val sameValve: Boolean) : ScanAction(state) {
    private var previousValve: Valve? = null
    private var previousStepsValue: Int? = null
    private var lockedValve: Valve? = if (sameValve) newValve else null

    private val str = "${userIndex}) Crossing tunnel from ${state.currentValve[userIndex]} to $newValve (Cost = $cost)"
    override fun toString(): String {
        return str
    }

    override fun execute() {

        val currentValve = state.currentValve[userIndex]


        state.remainingMinutes = if (decreaseMinute) state.remainingMinutes - 1 else state.remainingMinutes

        previousValve = currentValve
        state.currentValve[userIndex] = newValve

        if (lockedValve != null) {
            state.maxId = lockedValve!!.id
        }

        if (logActions) {
            state.actions.add(this)
        }
        state.visitedSinceLastOpening[userIndex].add(newValve)

        state.crossedArcs.putIfAbsent(currentValve, HashSet())
        state.crossedArcs[currentValve]!!.add(newValve)

        previousStepsValue = state.crossingTunnelSteps[userIndex]
        state.crossingTunnelSteps[userIndex] = cost - 1

        state.nextUserTurn = if (userIndex == 0) 1 else 0
    }


    override fun undo() {

        state.nextUserTurn = if (userIndex == 0) 1 else 0

        state.remainingMinutes = if (decreaseMinute) state.remainingMinutes + 1 else state.remainingMinutes
        state.currentValve[userIndex] = previousValve!!
        state.visitedSinceLastOpening[userIndex].remove(newValve)
        state.crossedArcs[previousValve]!!.remove(newValve)

        state.crossingTunnelSteps[userIndex] = previousStepsValue!!

        if (lockedValve != null) {
            state.maxId = null
        }
        if (logActions) {
            state.actions.remove(this)
        }

    }
}

class KeepTunnelCrossing(state: ScanState) : ScanAction(state) {
    private var previousUser: Int? = null
    private val str = "${userIndex}) Keep crossing tunnel to valve ${state.currentValve[userIndex]}"
    override fun toString(): String {
        return str
    }

    override fun execute() {

        state.crossingTunnelSteps[userIndex]--
        state.remainingMinutes = if (decreaseMinute) state.remainingMinutes - 1 else state.remainingMinutes

        if (logActions) {
            state.actions.add(this)
        }
        previousUser = state.nextUserTurn
        state.nextUserTurn = if (userIndex == 0) 1 else 0

    }

    override fun undo() {
        state.nextUserTurn = if (userIndex == 0) 1 else 0

        state.crossingTunnelSteps[userIndex]++
        state.remainingMinutes = if (decreaseMinute) state.remainingMinutes + 1 else state.remainingMinutes


        if (logActions) {
            state.actions.remove(this)
        }

    }
}

//class StayAtCurrentValve(userIndex: Int) : ScanAction(userIndex) {
//    override fun execute(state: ScanState): ScanState {
//        return ScanState(
//            currentValve = state.currentValve,
//            remainingMinutes = state.remainingMinutes - 1,
//            releasedPressure = state.releasedPressure,
//            openedValves = state.openedValves,
//            actions = state.actions + this,
//            visitedSinceLastOpening = state.visitedSinceLastOpening
//        )
//    }
//
//}