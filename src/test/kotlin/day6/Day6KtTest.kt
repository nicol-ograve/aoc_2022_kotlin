package day6

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day6KtTest {

    @Test
    fun findFirstMarker4() {
        assertEquals(findFirstMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 4), 7)
        assertEquals(findFirstMarker("bvwbjplbgvbhsrlpgdmjqwftvncz", 4), 5)
        assertEquals(findFirstMarker("nppdvjthqldpwncqszvftbrmjlhg", 4), 6)
        assertEquals(findFirstMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 4), 10)
        assertEquals(findFirstMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 4), 11)
    }

    @Test
    fun findFirstMarker14() {
        assertEquals(findFirstMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 14), 19)
        assertEquals(findFirstMarker("bvwbjplbgvbhsrlpgdmjqwftvncz", 14), 23)
        assertEquals(findFirstMarker("nppdvjthqldpwncqszvftbrmjlhg", 14), 23)
        assertEquals(findFirstMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 14), 29)
        assertEquals(findFirstMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 14), 26)
    }
}