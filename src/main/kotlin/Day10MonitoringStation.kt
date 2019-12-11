
import kotlin.math.abs

class Day10MonitoringStation {
    fun maxNumberOfOthersAsteroidsDetected(map: List<String>): Int {
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
            .map { numberOfOthersAsteroidsDetected(it, asteroidPositions) }
            .max() ?: 0
    }

    private fun numberOfOthersAsteroidsDetected(vector: Vector, asteroidVectors: MutableSet<Vector>): Int {
        return asteroidVectors
            .filter { it != vector }
            .map { relativePosition(it, vector) }
            .map { toDirection(it) }
            .distinct()
            .size
    }

    private fun toDirection(vector: Vector): Any {
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