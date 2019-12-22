import java.util.*
import kotlin.collections.HashMap

class Day20DonutMaze(val map: List<String>) {
    private val specialPoints = HashMap<Pair<Int, Int>, String>().toMutableMap()
    private val characterPattern = Regex("[A-Z]")

    init {
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (characterPattern.matches(map[y][x].toString())) {
                    recordSpecialPoint(x, y, map[y][x])
                }
            }
        }
    }

    private fun recordSpecialPoint(x: Int, y: Int, firstCharacter: Char) {
        if ((map.size > y + 1) && characterPattern.matches(map[y + 1][x].toString())) {
            if (map.size > y + 2 && map[y + 2][x] == '.') {
                specialPoints[Pair(x, y + 2)] = "$firstCharacter${map[y + 1][x]}"
            } else if (y > 0 && map[y - 1][x] == '.') {
                specialPoints[Pair(x, y - 1)] = "$firstCharacter${map[y + 1][x]}"
            }
        } else if (map[y].length > x + 1 && characterPattern.matches(map[y][x + 1].toString())) {
            if (map[y].length > x + 2 && map[y][x + 2] == '.') {
                specialPoints[Pair(x + 2, y)] = "$firstCharacter${map[y][x + 1]}"
            } else if (x > 0 && map[y][x - 1] == '.') {
                specialPoints[Pair(x - 1, y)] = "$firstCharacter${map[y][x + 1]}"
            }
        }
    }

    fun shortestPath(): Int {
        val possibleDirections = listOf(Pair(0, -1), Pair(0, 1), Pair(-1, 0), Pair(1, 0))
        val visitedPositions = HashSet<Pair<Int, Int>>()
        val explorations = ArrayDeque<Pair<Pair<Int, Int>, Int>>()
        explorations.add(Pair(specialPoints.entries.find { it.value == "AA" }!!.key, 0))
        while(explorations.isNotEmpty()) {
            val (position, steps) = explorations.poll()
            if (specialPoints[position] == "ZZ") {
                return steps
            }
            for (dir in possibleDirections) {
                val newPosition = Pair(position.first + dir.first, position.second + dir.second)
                if (visitedPositions.contains(newPosition)) {
                    continue
                }
                if (map[newPosition.second][newPosition.first] == '.') {
                    visitedPositions.add(newPosition)
                    explorations.add(Pair(newPosition, steps + 1))
                }
            }
            if (specialPoints.containsKey(position) && specialPoints[position] != "AA") {
                val connectedPosition = specialPoints.entries
                    .filter { it.key != position }
                    .find { it.value == specialPoints[position] }!!
                    .key
                if (!visitedPositions.contains(connectedPosition)) {
                    visitedPositions.add(connectedPosition)
                    explorations.add(Pair(connectedPosition, steps + 1))
                }
            }
        }
        return 0
    }

}