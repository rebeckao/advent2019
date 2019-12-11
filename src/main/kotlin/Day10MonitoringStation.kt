import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan

class Day10MonitoringStation {
    fun maxNumberOfOthersAsteroidsDetected(map: List<String>): Int {
        val asteroidPositions = toPositions(map)
        return asteroidPositions
            .map { numberOfOthersAsteroidsDetected(it, asteroidPositions) }
            .max() ?: 0
    }

    private fun toPositions(map: List<String>): MutableSet<Vector> {
        val asteroidPositions = HashSet<Vector>().toMutableSet()
        for (y in map.indices) {
            val row = map[y]
            for (x in row.indices) {
                if (row[x] == '#') {
                    asteroidPositions.add(Vector(x = x, y = y))
                }
            }
        }
        return asteroidPositions
    }

    private fun numberOfOthersAsteroidsDetected(vector: Vector, asteroidVectors: MutableSet<Vector>): Int {
        return relativeDirectionsToOtherAsteroids(vector, asteroidVectors)
            .map { toDirection(it) }
            .distinct()
            .size
    }

    fun asteroidDestroyed(map: List<String>, asteroidNumber: Int): Vector {
        val asteroidPositions = toPositions(map)
        val laserPosition = asteroidPositions.maxBy { numberOfOthersAsteroidsDetected(it, asteroidPositions) }!!
        asteroidPositions.remove(laserPosition)
        val leftToDestroy = relativeDirectionsToOtherAsteroids(laserPosition, asteroidPositions)
        var latestDestroyed = Vector(0, 0)
        var laserDirection = Vector(x = -1, y = -(map.size + 1))
        var numberOfDestroyed = 0
        while (leftToDestroy.isNotEmpty()) {
            latestDestroyed = asteroidPositions
                .groupBy { relativeDirection(observer = laserPosition, observed = it) }
                .minBy { rotationDistanceBetween(laserDirection, it.key) }!!
                .value
                .minBy { manhattanDistance(laserPosition, it) }!!
            laserDirection = relativeDirection(observer = laserPosition, observed = latestDestroyed)
            asteroidPositions.remove(latestDestroyed)
            numberOfDestroyed++
            if (numberOfDestroyed == asteroidNumber) {
                return latestDestroyed
            }
        }
        return latestDestroyed
    }

    private fun relativeDirection(
        observer: Vector,
        observed: Vector
    ) = toDirection(relativePosition(observer = observer, observed = observed))

    private fun manhattanDistance(
        pos1: Vector,
        pos2: Vector
    ): Int {
        val vector = relativePosition(pos1, pos2)
        return abs(vector.x) + abs(vector.y)
    }

    private fun rotationDistanceBetween(laserDirection: Vector, asteroidDirection: Vector): Double {
        if (laserDirection == asteroidDirection) {
            return 2 * PI
        }
        val laserDistance = radians(asteroidDirection)
        val asteroidDistance = radians(laserDirection)
        val difference = laserDistance - asteroidDistance
        return if (difference > 0) {
            difference
        } else {
            difference + 2 * PI
        }
    }

    fun radians(direction: Vector): Double {
        val x = direction.x
        val y = direction.y
        if (x >= 0 && y < 0) {
            return atan(x.toDouble() / -y.toDouble())
        } else if (x > 0 && y >= 0) {
            return atan(y.toDouble() / x.toDouble()) + PI / 2
        } else if (x <= 0 && y > 0) {
            return atan(-x.toDouble() / y.toDouble()) + PI
        } else if (x < 0 && y <= 0) {
            return atan(y.toDouble() / x.toDouble()) + 3 * PI / 2
        }
        throw IllegalStateException()
    }

    private fun relativeDirectionsToOtherAsteroids(
        vector: Vector,
        asteroidVectors: MutableSet<Vector>
    ): List<Vector> {
        return asteroidVectors
            .filter { it != vector }
            .map { relativePosition(it, vector) }
    }

    private fun toDirection(vector: Vector): Vector {
        val divisor = greatestCommonDivisorPreservingSign(vector.x, vector.y)
        return Vector(x = vector.x / divisor, y = vector.y / divisor)
    }

    fun greatestCommonDivisorPreservingSign(a: Int, b: Int): Int {
        return if (b == 0) abs(a) else greatestCommonDivisorPreservingSign(b, a % b)
    }

    private fun relativePosition(
        observed: Vector,
        observer: Vector
    ) = Vector(x = observed.x - observer.x, y = observed.y - observer.y)

    data class Vector(val x: Int, val y: Int)
}