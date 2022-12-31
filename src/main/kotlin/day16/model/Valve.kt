package day16.model

class Valve(val id: String, val flowRate:Int) {

    var reachableValves: List<Valve> = emptyList()
        private set

    fun addReachableValves(neighbors: List<Valve>){
        reachableValves = neighbors
    }

    override fun toString(): String {
        return "Valve $id"
    }
}