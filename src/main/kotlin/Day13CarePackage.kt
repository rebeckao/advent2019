
import Util.Companion.toLongArray
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.streams.toList

fun main(args: Array<String>) {
    val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day13.txt")))
    program[0] = 2

    val instructions = Files.lines(Paths.get("./src/test/resources/day13_instructions.txt"))
        .map{if (it == "a") "-1" else it}
        .map{if (it == "d") "1" else it}
        .map{if (it == "s") "0" else it}
        .map{if (it == "") "0" else it}
        .mapToInt{it.toInt()}
        .toList()
    val inputs = ArrayDeque<Int>()
    inputs.addAll(instructions)
    val recordedInputsAfterHardcoded = ArrayList<Int>().toMutableList()
    println("Final score by hardcoded instructions (+user) = ${Day13CarePackage().instructionsPlayGame(program, inputs, recordedInputsAfterHardcoded)}")
    println("recorded inputs = \n${recordedInputsAfterHardcoded.joinToString("\n")}")

//    println("Final score by robot = ${Day13CarePackage().robotPlayGame(program)}")

//    val recordedInputs = ArrayList<Int>().toMutableList()
//    println("Final score by user = ${Day13CarePackage().userPlayGame(program, recordedInputs)}")
//    println("recorded inputs = \n${recordedInputs.joinToString("\n")}")
}

class Day13CarePackage() {

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

    fun robotPlayGame(program: LongArray):Long{
        return playGame(program) {robotInput(it).toLong()}
    }

    fun userPlayGame(program: LongArray, recordedInputs : MutableList<Int>):Long{
        return playGame(program) {getAndRecordManualInput(it, recordedInputs).toLong()}
    }

    fun instructionsPlayGame(
        program: LongArray,
        instructions: Queue<Int>,
        recordedInputsAfterHardcoded: MutableList<Int>
    ):Long{
        return playGame(program) {hardcodedInput(it, instructions, recordedInputsAfterHardcoded).toLong()}
    }

    fun playGame(program: LongArray, inputProvider:(Map<Pair<Long, Long>, Int>)->Long): Long {
        val tiles = HashMap<Pair<Long, Long>, Int>().toMutableMap()
        var score = 0L
        val intComputer = IntComputer(program)
        var nextOutput = intComputer.nextOutputWithProvider({ inputProvider(tiles) }, 0, 0)
        while (!nextOutput.done) {
            val x = nextOutput.output!!
            nextOutput =
                intComputer.nextOutputWithProvider({ inputProvider(tiles) }, nextOutput.position, nextOutput.relativeBase)
            val y = nextOutput.output!!
            nextOutput =
                intComputer.nextOutputWithProvider({ inputProvider(tiles) }, nextOutput.position, nextOutput.relativeBase)
            val tile = nextOutput.output!!
            if (x == -1L && y == 0L) {
                score = tile
                println("Current score = ${score}")
            } else {
                tiles[Pair(x, y)] = tile.toInt()
            }
            nextOutput =
                intComputer.nextOutputWithProvider({ inputProvider(tiles) }, nextOutput.position, nextOutput.relativeBase)
        }
        return score
    }

    private fun hardcodedInput(tiles: Map<Pair<Long, Long>, Int>, instructions: Queue<Int>, recordedInputs: MutableList<Int>): Int {
        val nextInstruction = instructions.poll()
        if (nextInstruction != null ) {
            return nextInstruction
        }
        return getAndRecordManualInput(tiles, recordedInputs)
    }

    private fun robotInput(tiles: Map<Pair<Long, Long>, Int>): Int {
        draw(tiles)
        val ballXCoord = tiles.entries.find { it.value == 4}!!.key.first
        val tileXCoord = tiles.entries.find { it.value == 3}!!.key.first
        if (ballXCoord > tileXCoord){
            return 1
        }
        else if (ballXCoord < tileXCoord){
            return -1
        }
        else {
            return 0
        }
    }

    private fun getAndRecordManualInput(
        tiles: Map<Pair<Long, Long>, Int>,
        recordedInputs: MutableList<Int>
    ):Int {
        val manualInput = manualInput(tiles, recordedInputs)
        recordedInputs.add(manualInput)
        return manualInput
    }

    private fun manualInput(
        tiles: Map<Pair<Long, Long>, Int>,
        recordedInputs: MutableList<Int>
    ):Int {
        draw(tiles)
        println("Make your move:")
        val manualInstruction = readLine()!!
        if (manualInstruction == "0" || manualInstruction == "1" || manualInstruction == "-1") {
            return manualInstruction.toInt()
        } else if (manualInstruction == "a") {
            return -1
        } else if (manualInstruction == "s") {
            return 0
        } else if (manualInstruction == "d") {
            return 1
        } else if (manualInstruction == "save") {
            println("Your inputs = \n${recordedInputs.joinToString ("\n")}")
            recordedInputs.clear()
            return manualInput(tiles, recordedInputs)
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