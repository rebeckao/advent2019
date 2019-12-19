
import java.util.*
import kotlin.collections.HashMap

class Day17SetAndForget {

    fun scaffoldIntersectionCalibration(program: LongArray): Int {
        val input = ArrayDeque<Long>()
        val map = runProgram(program, input)
        return scaffoldIntersectionCalibration(map)
    }

    private fun runProgram(program: LongArray, input: ArrayDeque<Long>): String {
        val intComputer = IntComputer(program)
        var nextOutput = intComputer.nextOutput(input, 0, 0)
        var x = 0
        var y = 0
        var currentRow = StringBuilder()
        val allOutput = StringBuilder()
        while (!nextOutput.done) {
            val output = nextOutput.output!!
            when {
                output > 255 -> {
                    currentRow.append(output)
                }
                output == 10L -> {
                    y++
                    x = 0
                    println(currentRow)
                    allOutput.append(currentRow).append("\n")
                    currentRow = StringBuilder()
                }
                else -> {
                    currentRow.append(output.toChar())
                    x++
                }
            }
            nextOutput = intComputer.nextOutput(input, nextOutput.position, nextOutput.relativeBase)
        }
        allOutput.append(currentRow.toString())
        return allOutput.toString()
    }

    private fun toMap(stringMap : String) : Map<Pair<Int, Int>, Char> {
        val map = HashMap<Pair<Int, Int>, Char>().toMutableMap()
        val listMap = stringMap.split("\n")
        for (y in listMap.indices) {
            for (x in listMap[y].indices) {
                map[Pair(x, y)] = listMap[y][x]
            }
        }
        return map
    }

    fun scaffoldIntersectionCalibration(stringMap : String): Int {
        val map = toMap(stringMap)
        val intersections = map.filter { isIntersection(it, map) }
            .map { it.key }
        println("intersections = ${intersections.size}")
        drawWithIntersections(map, intersections.toSet())
        val alignmentParameters = intersections
            .map { it.first * it.second }
        return alignmentParameters
            .sum()
    }

    fun navigateAsInstructed(program: LongArray, mainMovementRoutine: String, programDefinitions: List<String>, continuousVideoFeed: Boolean): Long {
        val input = ArrayDeque<Long>()
        mainMovementRoutine.forEach { input.add(it.toLong()) }
        input.add(10)
        programDefinitions.forEach {
            it.forEach { c -> input.add(c.toLong()) }
            input.add(10)
        }
        input.add((if (continuousVideoFeed) 'y' else 'n').toLong())
        input.add(10)

        return runProgram(program, input)
            .split("\n")
            .last()
            .toLong()
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