package day13

import day13.model.IntPacket
import day13.model.ListPacket
import day13.model.Packet

fun arePacketsInRightOrder(left: Packet, right: Packet): Boolean? {
    if(left is IntPacket && right is IntPacket){
        println("- Compare ${left.value} vs ${right.value}")
        if(left.value == right.value){
            return null
        }
        return left.value < right.value
    } else if(left is IntPacket && right is ListPacket){
        println("- Compare ${left.value} vs ${right.value}")
        return isRightOrder(ListPacket(left.value), right)
    } else if(left is ListPacket && right is IntPacket){
        println("- Compare ${left.value} vs ${right.value}")
        return isRightOrder(left, ListPacket(right.value))
    } else {
        return isRightOrder(left as ListPacket, right as ListPacket)
    }
}

fun isRightOrder(left: ListPacket, right: ListPacket): Boolean? {
    println("- Compare ${left.value} vs ${right.value}")

    val leftIterator = left.iterator()
    val rightIterator = right.iterator()

    while (leftIterator.hasNext()) {

        if(!rightIterator.hasNext()){
            return false
        }

        val nextLeftPacket = leftIterator.next()
        val nextRightPacket = rightIterator.next()

        val rightOrder = arePacketsInRightOrder(nextLeftPacket, nextRightPacket)
        if(rightOrder != null) {
            return rightOrder
        }

    }
    if(rightIterator.hasNext()){
        return true
    }

    return null
}
