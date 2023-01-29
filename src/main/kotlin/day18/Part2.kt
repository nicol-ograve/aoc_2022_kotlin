package day18

import day18.model.Point3D

class Logic(private val points: Array<Point3D>) {

    private val maxes = findMaxes()
    private val blocks = points.toSet()

    fun execute(): Int {

        val maybeBlocked = initMaybeBlockedSet(maxes)
        blocks.forEach { maybeBlocked.remove(it) }



        maxes.forEachIndexed { index, i ->

            for (a in 1..maxes[(index + 1) % 3]) {
                for (b in 1..maxes[(index + 2) % 3]) {
                    var hasBlockBefore = false
                    for (c in 1..i) {
                        val x = if (index == 0) c else if (index == 1) b else a
                        val y = if (index == 1) c else if (index == 2) b else a
                        val z = if (index == 2) c else if (index == 0) b else a
                        val point = Point3D(x, y, z)

                        if (blocks.contains(point)) {
                            hasBlockBefore = true
                        } else if (!hasBlockBefore) {
                            maybeBlocked.remove(point)
                        }

                    }
                }
            }


        }

        do {
            val newFreePoints = hashSetOf<Point3D>()

            maybeBlocked.forEach {

                fun isFree(checkedPoint: Point3D): Boolean {
                    if (!blocks.contains(checkedPoint) && !maybeBlocked.contains(checkedPoint)) {
                        newFreePoints.add(it)
                        return true
                    }
                    return false
                }

                var point = it
                do {
                    point = Point3D(point.x - 1, point.y, point.z)
                    if (isFree(point)) {
                        return@forEach
                    }
                } while (point.x > 0 && !blocks.contains(point))

                point = it
                do {
                    point = Point3D(point.x + 1, point.y, point.z)
                    if (isFree(point)) {
                        return@forEach
                    }
                } while (point.x <= maxes[0] && !blocks.contains(point))

                point = it
                do {
                    point = Point3D(point.x, point.y - 1, point.z)
                    if (isFree(point)) {
                        return@forEach
                    }
                } while (point.y > 0 && !blocks.contains(point))

                point = it
                do {
                    point = Point3D(point.x, point.y + 1, point.z)
                    if (isFree(point)) {
                        return@forEach
                    }
                } while (point.y <= maxes[1] && !blocks.contains(point))

                point = it
                do {
                    point = Point3D(point.x, point.y, point.z - 1)
                    if (isFree(point)) {
                        return@forEach
                    }
                } while (point.z > 0 && !blocks.contains(point))

                point = it
                do {
                    point = Point3D(point.x, point.y, point.z + 1)
                    if (isFree(point)) {
                        return@forEach
                    }
                } while (point.z <= maxes[2] && !blocks.contains(point))


            }

            newFreePoints.forEach {
                if (!maybeBlocked.contains(it)) {
                    println("Error")
                }
                maybeBlocked.remove(it)
            }
        } while (newFreePoints.isNotEmpty())

//        val clusters = findClusters(maybeBlocked)

        return checkClustersSize(maybeBlocked)
    }

    private fun checkClustersSize(blockedPoints: HashSet<Point3D>): Int {
        val pointsToScan = mutableListOf<Point3D>()

        var facesBlocked = 0




        while (blockedPoints.isNotEmpty()) {
            if (pointsToScan.isEmpty()) {
                pointsToScan.add(blockedPoints.first())
            }

            val point = pointsToScan.elementAt(0)
            pointsToScan.removeAt(0)
            blockedPoints.remove(point)

            val nearPoints = point.nearPoints()
            nearPoints.filter { it.coordinates.all { c -> c > 0 } && it.x <= maxes[0] && it.y <= maxes[1] && it.z <= maxes[2] }
                .forEach {
                    if (blocks.contains(it)){
                        facesBlocked++
                    } else if(blockedPoints.contains(it)){
                        pointsToScan.add(it)
                        blockedPoints.remove(it)
                    }
                }

        }

        return facesBlocked

    }

    private fun findMaxes(): Array<Int> {
        val maxes = arrayOf(0, 0, 0)
        for (point in points) {
            point.coordinates.forEachIndexed { index, i ->
                if (i > maxes[index]) {
                    maxes[index] = i
                }
            }
        }
        return maxes
    }


    private fun initMaybeBlockedSet(maxes: Array<Int>): HashSet<Point3D> {
        val set = hashSetOf<Point3D>()

        for (x in 2..maxes[0]) {
            for (y in 2..maxes[1]) {
                for (z in 2..maxes[2]) {
                    set.add(Point3D(x, y, z))
                }
            }
        }

        return set
    }

}