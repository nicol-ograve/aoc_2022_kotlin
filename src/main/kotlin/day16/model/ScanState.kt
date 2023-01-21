package day16.model

data class ScanState(
    var nextUserTurn: Int,
    var currentValve: MutableList<Valve>,
    var remainingMinutes: Int,
    var releasedPressure: Int,
    var openedValves: MutableSet<Valve>,
    var actions: MutableList<ScanAction>,
    var visitedSinceLastOpening: MutableList<MutableSet<Valve>>,
    var crossedArcs: HashMap<Valve, HashSet<Valve>>,
    var crossingTunnelSteps: Array<Int>,
    var maxId: String? = null
) {
    override fun toString(): String {
        return "Valve: $currentValve, minutes: $remainingMinutes, pressure: $releasedPressure\nOpened: $openedValves"
    }
}