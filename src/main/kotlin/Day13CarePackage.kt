import java.util.*
import kotlin.collections.HashMap

class Day13CarePackage {
    fun numberOfBlockTiles(program : LongArray) : Int {
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
        return tiles.values.filter {it == 2 }.count()
    }
}