package shared


sealed interface Move {
    fun execute(startPoint: Point, times: Int = 1): Point
    val reverse: Move
}

object Up : Move {
    override fun execute(startPoint: Point, times: Int): Point {
        return startPoint.copy(y = startPoint.y + 1)
    }

    override val reverse: Move
        get() = Down
}

object Down : Move {
    override fun execute(startPoint: Point, times: Int): Point {
        return startPoint.copy(y = startPoint.y - times)
    }

    override val reverse: Move
        get() = Up
}

object Left : Move {
    override fun execute(startPoint: Point, times: Int): Point {
        return startPoint.copy(x = startPoint.x - 1)
    }

    override val reverse: Move
        get() = Right
}

object Right : Move {
    override fun execute(startPoint: Point, times: Int): Point {
        return startPoint.copy(x = startPoint.x + 1)
    }

    override val reverse: Move
        get() = Left
}


object DownRight : Move {
    override fun execute(startPoint: Point, times: Int): Point {
        return startPoint.copy(x = startPoint.x + 1, y = startPoint.y - 1)
    }

    override val reverse: Move
        get() = TopLeft
}


object DownLeft : Move {
    override fun execute(startPoint: Point, times: Int): Point {
        return startPoint.copy(x = startPoint.x - 1, y = startPoint.y - 1)
    }

    override val reverse: Move
        get() = TopRight
}

object TopLeft : Move {
    override fun execute(startPoint: Point, times: Int): Point {
        return startPoint.copy(x = startPoint.x - 1, y = startPoint.y + 1)
    }

    override val reverse: Move
        get() = DownRight
}

object TopRight : Move {
    override fun execute(startPoint: Point, times: Int): Point {
        return startPoint.copy(x = startPoint.x + 1, y = startPoint.y + 1)
    }

    override val reverse: Move
        get() = DownLeft
}

