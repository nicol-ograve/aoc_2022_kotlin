package day3

data class Rucksack(val content: String) {
    val firstCompartmentItems = content.substring(0, content.length / 2)
    val secondCompartmentItems = content.substring(content.length / 2)
}