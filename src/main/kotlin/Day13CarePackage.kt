import Util.Companion.toLongArray
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.HashMap

fun main(args: Array<String>) {
    val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day13.txt")))
    program[0] = 2
    Day13CarePackage().playGame(program)
}

class Day13CarePackage {
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
            tiles[Pair(x, y)] = tile.toInt()
            nextOutput =
                intComputer.nextOutputWithProvider({ manualInput(tiles) }, nextOutput.position, nextOutput.relativeBase)
        }
        return 0
    }

    private fun manualInput(tiles: MutableMap<Pair<Long, Long>, Int>): Long {
        draw(tiles)
        println("Asked for manual input")
        return readLine()!!.toLong()
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