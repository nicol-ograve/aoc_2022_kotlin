package day15.model

import day15.taxicabDistance
import shared.Point

class Sensor( val location: Point,  val nearestBeaconLocation: Point) {
    val radius = taxicabDistance(location, nearestBeaconLocation)

    private val yRange = Range(location.y - radius, location.y + radius)

    fun beaconFreeRangeForY(y: Int): Range?{
        val distance = Math.abs(location.y - y)
        if(distance <= radius){
            val rangeWidth = Math.abs(distance - radius)
            return Range(location.x - rangeWidth, location.x + rangeWidth)
        } else {
            return null
        }
    }

}