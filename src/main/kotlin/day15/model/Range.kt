package day15.model

class Range(val start: Int, val end: Int) {
    fun contains(value: Int): Boolean {
        return value in (start + 1) .. end
    }

    override fun toString(): String {
        return "$start --> $end"
    }
}