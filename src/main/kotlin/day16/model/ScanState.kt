package day16.model

data class ScanState(
    val nextUserTurn: Int,
    val currentValve: List<Valve>,
    val remainingMinutes: Int,
    val releasedPressure: Int,
    val openedValves: Set<Valve>,
    val actions: List<ScanAction>,
    val visitedSinceLastOpening: List<Set<Valve>>,
    val crossedArcs: HashMap<Valve, MutableList<Valve>>
) {
    override fun toString(): String {
        return "Valve: $currentValve, minutes: $remainingMinutes, pressure: $releasedPressure\nOpened: $openedValves"
    }
}