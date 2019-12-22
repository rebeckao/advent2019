import java.util.*

class Day19TractorBeam(val program: LongArray) {
    fun pointsAffectedByTractorBeam(): Int {
        var affectedPoints = 0
        for (y in 0..49) {
            for (x in 0..49) {
                val output = affectedByBeam(x, y)
                affectedPoints += output
                print(output)
            }
            println()
        }
        return affectedPoints
    }

    private fun affectedByBeam(x: Int, y: Int): Int {
        val intComputer = IntComputer(program.clone())
        val input = ArrayDeque<Long>()
        input.add(x.toLong())
        input.add(y.toLong())
        val output = intComputer.nextOutput(input).output?.toInt() ?: 0
        return output
    }

    fun closestSquareThatFitsInBeam(): Int {
        val (x, y) = lookForEvenCloserCorner(closestCornerInitialGuess())
        return x * 10_000 + y
    }

    private fun lookForEvenCloserCorner(initialClosestCorner: Pair<Int, Int>): Pair<Int, Int> {
        var (x, y) = initialClosestCorner
        while (true) {
            when {
                fitsInBeam(x - 1, y - 1) -> {
                    x -= 1
                    y -= 1
                }
                fitsInBeam(x - 2, y - 1) -> {
                    x -= 2
                    y -= 1
                }
                fitsInBeam(x - 1, y - 2) -> {
                    x -= 1
                    y -= 2
                }
                else -> {
                    return Pair(x, y)
                }
            }
        }
    }

    private fun fitsInBeam(x: Int, y: Int): Boolean {
        return affectedByBeam(x, y) == 1
                && affectedByBeam(x + 99, y) == 1
                && affectedByBeam(x, y + 99) == 1
                && affectedByBeam(x + 99, y + 99) == 1
    }

    private fun closestCornerInitialGuess(): Pair<Int, Int> {
        var previousX = 5_000
        var previousY = 10_000
        while (true) {
            val x = xOnEdge(previousX, previousY)
            val y = yOnEdge(x, previousY)
            if (x == previousX && y == previousY) {
                return Pair(x, y)
            } else {
                previousX = x
                previousY = y
            }
        }
    }

    private fun xOnEdge(initialMaxX: Int, y: Int): Int {
        if (affectedByBeam(initialMaxX, y + 99) != 1) {
            throw IllegalStateException()
        }
        var minX = 0
        var maxX = initialMaxX
        var x = maxX / 2
        while (true) {
            if (maxX - minX <= 1) {
                return maxX
            }
            val lowerLeftCorner = Pair(x, y + 99)
            if (affectedByBeam(lowerLeftCorner.first, lowerLeftCorner.second) == 1) {
                maxX = x
            } else {
                minX = x
            }
            x = (maxX - minX) / 2 + minX
        }
    }

    private fun yOnEdge(x: Int, initialMaxY: Int): Int {
        if (affectedByBeam(x + 99, initialMaxY) != 1) {
            throw IllegalStateException()
        }
        var minY = 0
        var maxY = initialMaxY
        var y = maxY / 2
        while (true) {
            if (maxY - minY <= 1) {
                return maxY
            }
            val upperRightCorner = Pair(x + 99, y)
            if (affectedByBeam(upperRightCorner.first, upperRightCorner.second) == 1) {
                maxY = y
            } else {
                minY = y
            }
            y = (maxY - minY) / 2 + minY
        }
    }
}