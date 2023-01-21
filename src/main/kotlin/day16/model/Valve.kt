package day16.model

import kotlin.properties.Delegates

open class Valve(val id: String, val flowRate: Int, ) {
    var index by Delegates.notNull<Int>()

    var opened = false

    var reachableValves: HashMap<Valve, Int> = HashMap()
        private set

    fun addNeighbor(valve: Valve) {
        reachableValves[valve] = 1
    }




    override fun toString(): String {
        return "Valve $id"
    }

}