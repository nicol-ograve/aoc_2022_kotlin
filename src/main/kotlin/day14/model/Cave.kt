package day14.model

import shared.*
import utils.printMatrix

class Cave(
    private val matrix: Matrix<CaveTile>,
    private val onFinished: () -> Unit,
    private val printSteps: Boolean = false
) {

    private var fallingSandPosition: Point? = null

    private val offsetX = matrix[0][0].position.x
    private val lastY = matrix.last()[0].position.y

    private val sandStartPoint = Point(500, 0)

    private val sandMoves = arrayOf(Down, DownLeft, DownRight)

    var fallenSandUnits = 0
        private set


    fun print() {
        printMatrix(matrix, ::printCaveTile)
    }

    fun moveSand() {
        if (fallingSandPosition == null) {
            createNewSandUnit()
        } else {
            val nextPosition = nextSandUnitPosition()
            if (nextPosition == null) {
                onFinished()
            } else if (nextPosition == fallingSandPosition) {
                if(nextPosition == sandStartPoint){
                    onFinished()
                }
                fallenSandUnits++
                fallingSandPosition = null
                if(printSteps){
                    print()
                }
            } else {
                val currentPosition = fallingSandPosition!!
                fallingSandPosition = nextPosition

                matrix[currentPosition] = AirTile(currentPosition)
                matrix[nextPosition] = SandUnit(nextPosition)
                if(printSteps){
                    print()
                }
            }

        }
    }


    private fun createNewSandUnit() {
        fallingSandPosition = sandStartPoint
        matrix[sandStartPoint] = SandUnit(sandStartPoint)
    }

    private fun nextSandUnitPosition(): Point? {
        for (move in sandMoves) {
            val nextPoint = move.execute(fallingSandPosition!!)
            if (nextPoint.x < offsetX || nextPoint.x > offsetX + matrix[0].size - 1 || nextPoint.y > lastY) {
                return null
            }
            if (matrix[nextPoint] is AirTile) {
                return nextPoint
            }
        }
        return fallingSandPosition!!
    }


    private operator fun <T> Matrix<T>.set(point: Point, value: T) {
        this[point.y][point.x - offsetX] = value
    }

    operator fun <T> Matrix<T>.get(point: Point): T {
        return this[point.y][point.x - offsetX]
    }

}


