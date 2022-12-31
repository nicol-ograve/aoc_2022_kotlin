package day4

import kotlin.math.max
import kotlin.math.min

data class Range(val start: Int, val end: Int) {
    init {
        if(start > end){
            throw RuntimeException("Invalid range")
        }
    }

    fun contains(other: Range): Boolean{
        return start <= other.start && end >= other.end
    }
    fun overlapsCount(other: Range): Int {
        if(start > other.end || end < other.start){
            return 0
        }

        return min(end, other.end) - max(start , other.start) + 1



    }
}