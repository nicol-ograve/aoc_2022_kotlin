package day14.model

import shared.Point
import java.lang.RuntimeException

sealed class CaveTile(val position: Point) {}

class RockBlock(position: Point) : CaveTile(position) {}
class SandUnit(position: Point) : CaveTile(position) {}
class SandSource(position: Point) : CaveTile(position) {}
class AirTile(position: Point) : CaveTile(position) {}

fun printCaveTile(tile: CaveTile): String {
    return when(tile){
        is RockBlock -> "#"
        is AirTile -> "."
        is SandUnit -> "O"
        is SandSource -> "+"
    }
}