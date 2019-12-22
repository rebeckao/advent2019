import java.util.*
import java.util.Collections.singletonList
import kotlin.collections.HashMap

class Day20DonutMaze(val map: List<String>) {
    private val specialPoints = HashMap<Pair<Int, Int>, Pair<String, Boolean>>().toMutableMap()
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
                specialPoints[Pair(x, y + 2)] = Pair("$firstCharacter${map[y + 1][x]}", isOuter(x, y + 2))
            } else if (y > 0 && map[y - 1][x] == '.') {
                specialPoints[Pair(x, y - 1)] = Pair("$firstCharacter${map[y + 1][x]}", isOuter(x, y - 1))
            }
        } else if (map[y].length > x + 1 && characterPattern.matches(map[y][x + 1].toString())) {
            if (map[y].length > x + 2 && map[y][x + 2] == '.') {
                specialPoints[Pair(x + 2, y)] = Pair("$firstCharacter${map[y][x + 1]}", isOuter(x + 2, y))
            } else if (x > 0 && map[y][x - 1] == '.') {
                specialPoints[Pair(x - 1, y)] = Pair("$firstCharacter${map[y][x + 1]}", isOuter(x - 1, y))
            }
        }
    }

    private fun isOuter(x: Int, y: Int): Boolean {
        return x < 3
                || x > map[0].length - 4
                || y < 3
                || y > map.size - 4
    }

    fun shortestPath(): Int {
        val possibleDirections = listOf(Pair(0, -1), Pair(0, 1), Pair(-1, 0), Pair(1, 0))
        val visitedPositions = HashSet<Pair<Int, Int>>()
        val explorations = ArrayDeque<Pair<Pair<Int, Int>, Int>>()
        explorations.add(Pair(specialPoints.entries.find { it.value.first == "AA" }!!.key, 0))
        while (explorations.isNotEmpty()) {
            val (position, steps) = explorations.poll()
            if (specialPoints[position]?.first == "ZZ") {
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
            if (specialPoints.containsKey(position) && specialPoints[position]?.first != "AA") {
                val connectedPosition = specialPoints.entries
                    .filter { it.key != position }
                    .find { it.value.first == specialPoints[position]?.first }!!
                    .key
                if (!visitedPositions.contains(connectedPosition)) {
                    visitedPositions.add(connectedPosition)
                    explorations.add(Pair(connectedPosition, steps + 1))
                }
            }
        }
        return 0
    }

    fun shortestPathAccountingForRecursion(): Int {
        val possibleDirections = listOf(Pair(0, -1), Pair(0, 1), Pair(-1, 0), Pair(1, 0))
        val visitedPositions = HashSet<Pair<Pair<Int, Int>, Int>>()
        val explorations = ArrayDeque<MazeState>()
        val startPosition = specialPoints.entries.find { it.value.first == "AA" }!!.key
        visitedPositions.add(Pair(startPosition, 0))
        explorations.add(MazeState(startPosition.first, startPosition.second, 0, 0, singletonList("AA(->0)")))
        while (explorations.isNotEmpty()) {
            val mazeState = explorations.poll()
            val position = Pair(mazeState.x, mazeState.y)
            val specialPointLabel = specialPoints[position]?.first
            if (specialPointLabel == "ZZ") {
                if (mazeState.level == 0) {
                    return mazeState.steps
                }
            }
            for (dir in possibleDirections) {
                val (newX, newY) = Pair(mazeState.x + dir.first, mazeState.y + dir.second)
                if (visitedPositions.contains(Pair(Pair(newX, newY), mazeState.level))) {
                    continue
                }
                if (map[newY][newX] == '.') {
                    visitedPositions.add(Pair(Pair(newX, newY), mazeState.level))
                    explorations.add(MazeState(newX, newY, mazeState.steps + 1, mazeState.level, mazeState.pathSoFar))
                }
            }
            if (specialPointLabel != null && specialPointLabel != "AA" && specialPointLabel != "ZZ") {
                val connectedPosition = specialPoints.entries
                    .filter { it.key != position }
                    .find { it.value.first == specialPointLabel }!!
                    .key
                val isOuterPort = specialPoints[position]!!.second
                if (isOuterPort && mazeState.level == 0) {
                    continue
                }
                val newLevel = mazeState.level + (if (isOuterPort) -1 else 1)
                if (!visitedPositions.contains(Pair(connectedPosition, newLevel))) {
                    visitedPositions.add(Pair(connectedPosition, newLevel))
                    val pathSoFar = mazeState.pathSoFar.toMutableList()
                    pathSoFar.add("$specialPointLabel(${mazeState.level}->$newLevel)")
                    explorations.add(
                        MazeState(
                            connectedPosition.first,
                            connectedPosition.second,
                            mazeState.steps + 1,
                            newLevel,
                            pathSoFar
                        )
                    )
                }
            }
        }
        return 0
    }

    data class MazeState(val x: Int, val y: Int, val steps: Int, val level: Int, val pathSoFar : List<String>)

}