
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class Day18ManyWorlds(val rawMap: String) {
    val paths: Map<Char, List<PathToKey>>
    init {
        this.paths = toPaths(rawMap)
    }

    fun shortestPathToCollectAllKeys(): Int {
//        val explorations = Stack<Exploration>() // Width first
        val explorations = ArrayDeque<Exploration>() // Breadth first
        explorations.add(Exploration('@', 0, HashSet()))
        val numberOfKeys = paths.keys.size - 1
        var minSteps = Int.MAX_VALUE
        var i = 0
        while (explorations.isNotEmpty()) {
            val exploration = explorations.pop()
            val keysCollected = exploration.keysCollected
            if (keysCollected.size == numberOfKeys) {
                minSteps = exploration.steps
                continue
            }
            paths[exploration.currentKey]!!
                .filter { !keysCollected.contains(it.to) }
                .filter { theWayIsCleared(it.blockedBy, keysCollected) }
                .filter { (exploration.steps + it.distance) < minSteps }
                .forEach {
                    val newKeysCollected = keysCollected.toMutableSet()
                    newKeysCollected.add(it.to)
                    explorations.add(Exploration(it.to, exploration.steps + it.distance, newKeysCollected))
                }
            if (i++ % 1000_000 == 0) {
                println("i = $i, minsteps = $minSteps, explorations=${explorations.size}")
            }
        }
        return minSteps
    }

    private fun theWayIsCleared(blockers: List<Char>, keysCollected: Set<Char>): Boolean {
        return blockers.filter { !keysCollected.contains(it) }.isEmpty()
    }

    private fun toPaths(rawMap: String): Map<Char, List<PathToKey>> {
        var initialMap = rawMap.split("\n")
        val allKeys = HashMap<Char, Pair<Int, Int>>()
        var startingPosition = Pair(0, 0)
        for (y in initialMap.indices) {
            for (x in initialMap[y].indices) {
                if (initialMap[y][x] == '@') {
                    startingPosition = Pair(x, y)
                }
                if (initialMap[y][x].toString().matches(Regex("[a-z]"))) {
                    allKeys[initialMap[y][x]] = Pair(x, y)
                }
            }
        }
        initialMap = initialMap.map { it.replace('@', '.') }
        val paths = HashMap<Char, MutableList<PathToKey>>().toMutableMap()
        paths['@'] = ArrayList()
        for (key in allKeys.keys) {
            paths[key] = ArrayList()
            for (otherKey in allKeys.keys) {
                if (key == otherKey) {
                    continue
                }
                paths[key]!!.add(findPathTo(otherKey, initialMap, allKeys[key]!!))
            }
            paths['@']!!.add(findPathTo(key, initialMap, startingPosition))
        }
        return paths
    }

    private fun findPathTo(
        target: Char,
        initialMap: List<String>,
        startPosition: Pair<Int, Int>
    ): PathToKey {
        val explorations = ArrayDeque<PathState>() // Breadth first
        explorations.add(PathState(startPosition, 0, setOf(startPosition), Collections.emptyList()))
        while (explorations.isNotEmpty()) {
            val pathState = explorations.poll()!!
            val currentPosition = pathState.position
            for (dir in Arrays.asList(Pair(0, -1), Pair(0, 1), Pair(-1, 0), Pair(1, 0))) {
                val newPosition = Pair(currentPosition.first + dir.first, currentPosition.second + dir.second)
                if (pathState.visitedPositions.contains(newPosition)) {
                    continue
                }
                val tile = initialMap[newPosition.second][newPosition.first]
                if (tile == '#') {
                    continue
                }
                val blockers = pathState.blockers.toMutableList()
                if (tile.toString().matches(Regex("[A-Z]"))) {
                    blockers.add(tile.toLowerCase())
                }
                if (tile == target) {
                    return PathToKey(target, pathState.steps + 1, blockers)
                } else {
                    val visitedPositions = pathState.visitedPositions.toMutableSet()
                    visitedPositions.add(newPosition)
                    explorations.add(PathState(newPosition, pathState.steps + 1, visitedPositions, blockers))
                }
            }
        }
        throw IllegalStateException()
    }

    data class PathToKey(val to: Char, val distance: Int, val blockedBy: List<Char>)

    data class PathState(
        val position: Pair<Int, Int>,
        val steps: Int,
        val visitedPositions: Set<Pair<Int, Int>>,
        val blockers: List<Char>
    )

    data class Exploration(val currentKey: Char, val steps: Int, val keysCollected: Set<Char>)

}