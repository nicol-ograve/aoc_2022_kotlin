package day15.model

class MultiRange(initialRange: Range) {

    var ranges = mutableListOf(initialRange)
        private set

    fun subtractRanges(deletedRanges: Collection<Range>) {
        deletedRanges.forEach(this::subtractRange)
    }

    fun subtractRange(deletedRange: Range) {
        val newList = mutableListOf<Range>()
        for (range in ranges) {
            if (deletedRange.start <= range.start && deletedRange.end >= range.end) {
                // Range is deleted by not adding it to the new list
            } else {
                if (range.contains(deletedRange.start)) {
                    if (range.contains(deletedRange.end)) {
                        /*
                       |-------| --> range
                         |---| --> deletedRange
                     */
                        newList.add(Range(range.start, deletedRange.start - 1))
                        newList.add(Range(deletedRange.end + 1, range.end))
                    } else {
                        /*
                       |-------| --> range
                          |-------| --> deletedRange
                     */
                        newList.add(Range(range.start, deletedRange.start - 1))
                    }
                } else if (range.contains(deletedRange.end)) {
                    /*
                       |-------| --> range
                 |-------| --> deletedRange
                 */
                    newList.add(Range(deletedRange.end + 1, range.end))
                } else {
                    // No intersection
                    newList.add(range)
                }
            }
        }
        ranges = newList

    }
}