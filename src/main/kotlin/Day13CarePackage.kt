import Util.Companion.toLongArray
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.HashMap
import kotlin.streams.toList

fun main(args: Array<String>) {
    val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day13.txt")))
    val instructions = Files.lines(Paths.get("./src/test/resources/day13_instructions.txt"))
        .map{if (it == "a") "-1" else it}
        .map{if (it == "d") "1" else it}
        .map{if (it == "s") "0" else it}
        .map{if (it == "") "0" else it}
        .mapToInt{it.toInt()}
        .toList()
    val inputs = ArrayDeque<Int>()
    inputs.addAll(instructions)
    program[0] = 2
    val finalScore = Day13CarePackage(inputs).playGame(program)
    println("Final score = ${finalScore}")
}

class Day13CarePackage(val instructions: Queue<Int>) {

    fun numberOfBlockTiles(program: LongArray): Int {
        val tiles = HashMap<Pair<Long, Long>, Int>().toMutableMap()
        val intComputer = IntComputer(program)
        var nextOutput = intComputer.nextOutput(ArrayDeque(), 0, 0)
        while (!nextOutput.done) {
            val x = nextOutput.output!!
            nextOutput = intComputer.nextOutput(ArrayDeque(), nextOutput.position, nextOutput.relativeBase)
            val y = nextOutput.output!!
            nextOutput = intComputer.nextOutput(ArrayDeque(), nextOutput.position, nextOutput.relativeBase)
            val tile = nextOutput.output!!
            tiles[Pair(x, y)] = tile.toInt()
            nextOutput = intComputer.nextOutput(ArrayDeque(), nextOutput.position, nextOutput.relativeBase)
        }
        return tiles.values.filter { it == 2 }.count()
    }

    fun playGame(program: LongArray): Long {
        val tiles = HashMap<Pair<Long, Long>, Int>().toMutableMap()
        var score = 0L
        val intComputer = IntComputer(program)
        var nextOutput = intComputer.nextOutputWithProvider({ manualInput(tiles) }, 0, 0)
        while (!nextOutput.done) {
            val x = nextOutput.output!!
            nextOutput =
                intComputer.nextOutputWithProvider({ manualInput(tiles) }, nextOutput.position, nextOutput.relativeBase)
            val y = nextOutput.output!!
            nextOutput =
                intComputer.nextOutputWithProvider({ manualInput(tiles) }, nextOutput.position, nextOutput.relativeBase)
            val tile = nextOutput.output!!
            if (x == -1L && y == 0L) {
                score = tile
                println("Current score = ${score}")
            } else {
                tiles[Pair(x, y)] = tile.toInt()
            }
            nextOutput =
                intComputer.nextOutputWithProvider({ manualInput(tiles) }, nextOutput.position, nextOutput.relativeBase)
        }
        return score
    }

    private fun manualInput(tiles: MutableMap<Pair<Long, Long>, Int>): Long {
        draw(tiles)
        val nextInstruction = this.instructions.poll()
        if (nextInstruction != null ) {
            return nextInstruction.toLong()
        }
        println("Make your move:")
        val manualInstruction = readLine()!!
        if (manualInstruction == "0" || manualInstruction == "1" || manualInstruction == "-1") {
            return manualInstruction.toLong()
        } else if (manualInstruction == "a") {
            return -1
        } else if (manualInstruction == "s") {
            return 0
        } else if (manualInstruction == "d") {
            return 1
        }
        return 0
    }

    private fun draw(initialGameState: Map<Pair<Long, Long>, Int>) {
        val minX = initialGameState.keys.map { it.first }.min()!!
        val minY = initialGameState.keys.map { it.second }.min()!!
        val maxX = initialGameState.keys.map { it.first }.max()!!
        val maxY = initialGameState.keys.map { it.second }.max()!!
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                print(representation(initialGameState[Pair(x, y)] ?: 0))
            }
            print("\n")
        }
    }

    private fun representation(tile: Int): String {
        return when (tile) {
            0 -> " "
            1 -> "¤"
            2 -> "#"
            3 -> "_"
            4 -> "@"
            else -> " "
        }
    }
}