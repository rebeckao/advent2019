
import java.util.*
import kotlin.collections.HashMap

class Day17SetAndForget {
    fun scaffoldIntersectionCalibration(program: LongArray): Int {
        val intComputer = IntComputer(program)
        var nextOutput = intComputer.nextOutput(ArrayDeque(), 0, 0)
        val map = HashMap<Pair<Int, Int>, Char>()
        var x = 0
        var y = 0
        while (!nextOutput.done) {
            val output = nextOutput.output!!
            if (output == 10L) {
                y++
                x = 0
            } else {
                if (output != 46L) {
                    map[Pair(x, y)] = output.toChar()
                }
                x++
            }
            nextOutput = intComputer.nextOutput(ArrayDeque(), nextOutput.position, nextOutput.relativeBase)
        }
        draw(map)
        return scaffoldIntersectionCalibration(map)
    }

    fun scaffoldIntersectionCalibration(map: Map<Pair<Int, Int>, Char>): Int {
        val intersections = map.filter { isIntersection(it, map) }
            .map{it.key}
        println("intersections = ${intersections.size}")
        drawWithIntersections(map, intersections.toSet())
        val alignmentParameters = intersections
            .map { it.first * it.second }
        return alignmentParameters
            .sum()
    }

    private fun isIntersection(tile: Map.Entry<Pair<Int, Int>, Char>, map: Map<Pair<Int, Int>, Char>): Boolean {
        if (!isScaffold(tile.value)) {
            return false
        }
        val (x, y) = tile.key
        return isScaffold(map[Pair(x - 1, y)])
                && isScaffold(map[Pair(x + 1, y)])
                && isScaffold(map[Pair(x, y - 1)])
                && isScaffold(map[Pair(x, y + 1)])
    }

    private fun isScaffold(tileValue: Char?): Boolean {
        return tileValue ?: '.' != '.'
    }

    private fun draw(map: Map<Pair<Int, Int>, Char>) {
        val minX = map.keys.map { it.first }.min()!!
        val minY = map.keys.map { it.second }.min()!!
        val maxX = map.keys.map { it.first }.max()!!
        val maxY = map.keys.map { it.second }.max()!!
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val currentPosition = Pair(x, y)
                val currentTile: Char = map[currentPosition] ?: ' '
                print(currentTile.toString())
            }
            print("\n")
        }
    }

    private fun drawWithIntersections(
        map: Map<Pair<Int, Int>, Char>,
        intersections: Set<Pair<Int, Int>>
    ) {
        val minX = map.keys.map { it.first }.min()!!
        val minY = map.keys.map { it.second }.min()!!
        val maxX = map.keys.map { it.first }.max()!!
        val maxY = map.keys.map { it.second }.max()!!
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val currentPosition = Pair(x, y)
                if (intersections.contains(currentPosition)) {
                    print("O")
                } else {
                    val currentTile: Char = map[currentPosition] ?: ' '
                    print(currentTile.toString())
                }
            }
            print("\n")
        }
    }
}