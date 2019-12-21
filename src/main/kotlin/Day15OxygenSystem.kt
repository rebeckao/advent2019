import Util.Companion.toLongArray
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.streams.toList

fun main(args: Array<String>) {
    val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day15.txt")))
    val savedMap = Files.lines(Paths.get("./src/test/resources/day15_map.txt")).toList()
    println("shortest path to oxygen = ${Day15OxygenSystem(savedMap).shortesPathFromStartToOxygen()}")
    println("longest path from oxygen = ${Day15OxygenSystem(savedMap).longestPathFromOxygen()}")
    Day15OxygenSystem(savedMap).pilotRobot(program)
//    Day15OxygenSystem(Collections.emptyList()).randomlyExplore(program, 1000_000)
}

class Day15OxygenSystem(savedMap: List<String>) {

    var robotPosition = Pair(0, 0)
    var oxygenPosition: Pair<Int, Int>? = null
    var direction = Pair(0, 0)
    val map: MutableMap<Pair<Int, Int>, Char> = HashMap<Pair<Int, Int>, Char>().toMutableMap()

    init {
        for (y in savedMap.indices) {
            val row = savedMap[y]
            for (x in row.indices) {
                val tile = row[x]
                val currentPosition = Pair(x, y)
                if (tile == '@') {
                    map[currentPosition] = '.'
                } else if (tile != ' ') {
                    map[currentPosition] = tile
                    if (tile == 'o') {
                        this.robotPosition = currentPosition
                    }
                    if (tile == 'x') {
                        this.oxygenPosition = currentPosition
                    }
                }
            }
        }
    }

    fun pilotRobot(program: LongArray) {
        map[robotPosition] = 'o'
        println("start position = (${robotPosition.first}, ${robotPosition.second})")
        val intComputer = IntComputer(program)
        var output = intComputer.nextOutputWithProvider({ manualInput() })
        while (!output.done) {
            val nextTile = Pair(robotPosition.first + direction.first, robotPosition.second + direction.second)
            if (output.output!! == 0L) {
                map[nextTile] = '#'
            } else if (output.output == 1L) {
                map[nextTile] = '.'
                robotPosition = nextTile
            } else if (output.output == 2L) {
                map[nextTile] = 'x'
                robotPosition = nextTile
            } else {
                throw IllegalStateException()
            }
            output = intComputer.nextOutputWithProvider({ manualInput() })
        }
    }

    fun randomlyExplore(program: LongArray, maxSteps: Int) {
        map[robotPosition] = 'o'
        println("start position = (${robotPosition.first}, ${robotPosition.second})")
        val intComputer = IntComputer(program)
        var output = intComputer.nextOutputWithProvider({ randomInput() })
        var steps = 0
        while (!output.done && steps < maxSteps) {
            steps ++
            val nextTile = Pair(robotPosition.first + direction.first, robotPosition.second + direction.second)
            if (output.output!! == 0L) {
                map[nextTile] = '#'
            } else if (output.output == 1L) {
                map[nextTile] = '.'
                robotPosition = nextTile
            } else if (output.output == 2L) {
                map[nextTile] = 'x'
                println("oxygen system = (${robotPosition.first}, ${robotPosition.second})")
                robotPosition = nextTile
            } else {
                throw IllegalStateException()
            }
            output = intComputer.nextOutputWithProvider({ randomInput() })
        }
        draw()
    }

    fun longestPathFromOxygen(): Int {
        val directions = Arrays.asList(Pair(0, -1), Pair(0, 1), Pair(-1, 0), Pair(1, 0))
        val explorations = ArrayDeque<Pair<Pair<Int, Int>, Int>>()
        explorations.add(Pair(oxygenPosition!!, 0))
        var maxSteps = 0
        var iter = 0
        while (explorations.isNotEmpty()) {
            iter++
            val nextExploration = explorations.poll()
            val currentPosition = nextExploration.first
            val steps = nextExploration.second
            if (steps > maxSteps) {
                maxSteps = steps
            }
            if (iter % 50 == 0) {
                draw()
                println("max steps = ${maxSteps}")
            }
            for (dir in directions) {
                val newPosition = Pair(currentPosition.first + dir.first, currentPosition.second + dir.second)
                if (map[newPosition] == '.') {
                    map[newPosition] = 'O'
                    explorations.add(Pair(newPosition, steps + 1))
                }
            }
        }
        draw()
        return maxSteps
    }

    fun shortesPathFromStartToOxygen(): Int {
        val visitedPositions = HashSet<Pair<Int, Int>>()
        val explorations = ArrayDeque<Pair<Pair<Int, Int>, Int>>()
        explorations.add(Pair(robotPosition, 0))
        while (explorations.isNotEmpty()) {
            val (currentPosition, steps) = explorations.poll()
            for (dir in Arrays.asList(Pair(0, -1), Pair(0, 1), Pair(-1, 0), Pair(1, 0))) {
                val newPosition = Pair(currentPosition.first + dir.first, currentPosition.second + dir.second)
                if (visitedPositions.contains(newPosition)) {
                    continue
                } else if (map[newPosition] == 'x') {
                    return steps + 1
                } else if (map[newPosition] == '.') {
                    visitedPositions.add(newPosition)
                    explorations.add(Pair(newPosition, steps + 1))
                }
            }
        }
        throw java.lang.IllegalStateException()
    }

    private fun randomInput(): Long {
        val randomDirection = Arrays.asList(1, 2, 3, 4)
            .filter {
                map[Pair(
                    robotPosition.first + toDirection(it).first,
                    robotPosition.second + toDirection(it).second
                )] ?: ' ' != '#'
            }
            .random()!!.toLong()
        direction = toDirection(randomDirection.toInt())
        return randomDirection
    }

    private fun toDirection(input: Int): Pair<Int, Int> {
        if (input == 1) {
            return Pair(0, -1)
        } else if (input == 2) {
            return Pair(0, 1)
        } else if (input == 3) {
            return Pair(-1, 0)
        } else if (input == 4) {
            return Pair(1, 0)
        }
        throw IllegalStateException()
    }

    private fun manualInput(): Long {
        draw()
        val latestInput = promptForDirection()
        direction = toDirection(latestInput.toInt())
        return latestInput
    }

    private fun promptForDirection(): Long {
        println("Make your move (wasd):")
        val manualInstruction = readLine()!!
        if (manualInstruction == "w") {
            return 1
        } else if (manualInstruction == "s") {
            return 2
        } else if (manualInstruction == "a") {
            return 3
        } else if (manualInstruction == "d") {
            return 4
        }
        return promptForDirection()
    }

    private fun draw() {
        val minX = map.keys.map { it.first }.min()!!
        val minY = map.keys.map { it.second }.min()!!
        val maxX = map.keys.map { it.first }.max()!!
        val maxY = map.keys.map { it.second }.max()!!
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val currentPosition = Pair(x, y)
                val currentTile: Char = map[currentPosition] ?: ' '
                if (robotPosition == currentPosition && currentTile != 'x') {
                    print("@")
                } else {
                    print(currentTile)
                }
            }
            print("\n")
        }
    }

}