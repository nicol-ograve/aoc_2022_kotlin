package day16.model


sealed class ScanAction(val state: ScanState, val userIndex: Int = 0) {
    abstract fun execute(decreaseMinute: Boolean): ScanState
}

class ReleasePressure(state: ScanState, userIndex: Int) : ScanAction(state, userIndex) {
    override fun toString(): String {
        return "${userIndex}) Releasing Pressure to ${state.currentValve[userIndex]}"
    }

    override fun execute(decreaseMinute: Boolean): ScanState {
        val valve = state.currentValve[userIndex]
        val remainingMinutes = if (decreaseMinute) state.remainingMinutes - 1 else state.remainingMinutes
        return state.copy(
            remainingMinutes = remainingMinutes,
            nextUserTurn = if (userIndex == 0) 1 else 0,
            releasedPressure = state.releasedPressure + remainingMinutes * valve.flowRate,
            openedValves = state.openedValves + valve,
            actions = state.actions + this,
            visitedSinceLastOpening = state.visitedSinceLastOpening.mapIndexed { index, it ->
                if (index == userIndex) {
                    HashSet<Valve>()
                } else {
                    it
                }
            },

            )
    }
}

class CrossTunnel(state: ScanState, val newValve: Valve, userIndex: Int) : ScanAction(state, userIndex) {
    override fun toString(): String {
    return "${userIndex}) Crossing tunnel from ${state.currentValve[userIndex]} to $newValve"
}

    override fun execute(decreaseMinute: Boolean): ScanState {

        val currentValve = state.currentValve[userIndex]
        val arcsMap = hashMapOf<Valve, MutableList<Valve>>()
        state.crossedArcs.keys.forEach {
            arcsMap[it] = state.crossedArcs[it]!!.toMutableList()
        }
        arcsMap.putIfAbsent(currentValve, mutableListOf())
        arcsMap[currentValve]!!.add(newValve)

        return state.copy(
            nextUserTurn = if (userIndex == 0) 1 else 0,
            remainingMinutes = if (decreaseMinute) state.remainingMinutes - 1 else state.remainingMinutes,
            currentValve = state.currentValve.mapIndexed { index, it ->
                if (index == userIndex) {
                    newValve
                } else {
                    it
                }
            },
            actions = state.actions + this,
            visitedSinceLastOpening = state.visitedSinceLastOpening
                .mapIndexed { index, it ->
                    if (index == userIndex) {
                        state.visitedSinceLastOpening[index] + newValve
                    } else {
                        it
                    }
                },
            crossedArcs = arcsMap
        )
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