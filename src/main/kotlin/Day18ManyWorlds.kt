
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class Day18ManyWorlds(rawMap: List<String>) {
    private val paths: Map<Char, List<PathToKey>>
    private val shortestPaths = HashMap<String, Int>().toMutableMap()
    private val numberOfKeys: Int
    private val startPaths: List<List<PathToKey>>
    private val map: List<String>

    init {
        val allKeys = HashMap<Char, Pair<Int, Int>>()
        val startingPositions = ArrayList<Pair<Int, Int>>()
        for (y in rawMap.indices) {
            for (x in rawMap[y].indices) {
                if (rawMap[y][x] == '@') {
                    startingPositions.add(Pair(x, y))
                } else {
                    if (rawMap[y][x].toString().matches(Regex("[a-z]"))) {
                        allKeys[rawMap[y][x]] = Pair(x, y)
                    }
                }
            }
        }
        this.numberOfKeys = allKeys.size
        this.map = rawMap.map { it.replace('@', '.') }
        val paths = HashMap<Char, List<PathToKey>>().toMutableMap()
        this.startPaths = startingPositions.map { findPaths(it) }
        startPaths.indices.forEach {
            paths[it.toString()[0]] = findPaths(startingPositions[it])
        }
        for (key in allKeys.keys) {
            paths[key] = findPaths(allKeys[key]!!)
        }
        this.paths = paths
    }

    fun shortestPathToCollectAllKeys(): Int {
        val startingTokens = startPaths.indices.map { it.toString()[0] }.toCharArray()
        return shortestPathToRemainingKeys(emptySet(), startingTokens)
    }

    private fun shortestPathToRemainingKeys(keysCollected: Set<Char>, currentKeys: CharArray): Int {
        if (keysCollected.size == numberOfKeys) {
            return 0
        }
        val hash = hash(currentKeys, keysCollected)
        var minSteps = Int.MAX_VALUE
        if (!shortestPaths.containsKey(hash)) {
            for (keyIndex in currentKeys.indices) {
                val possibePaths = paths[currentKeys[keyIndex]]!!
                    .filter { !keysCollected.contains(it.to) }
                    .filter { theWayIsCleared(it.blockedBy, keysCollected) }
                if (possibePaths.isEmpty()) {
                    continue
                }
                val shortestPath = possibePaths
                    .map {
                        val newCurrentKeys = currentKeys.clone()
                        newCurrentKeys[keyIndex] = it.to
                        it.distance + shortestPathToRemainingKeys(collectKey(keysCollected, it.to), newCurrentKeys)
                    }
                    .min()!!
                if (shortestPath < minSteps) {
                    minSteps = shortestPath
                }
            }
            shortestPaths[hash] = minSteps
        }
        return shortestPaths[hash]!!
    }

    private fun collectKey(keysCollectedIncoming: Set<Char>, newKey: Char): Set<Char> {
        val keysCollected = keysCollectedIncoming.toMutableSet()
        keysCollected.add(newKey)
        return keysCollected
    }

    private fun theWayIsCleared(blockers: List<Char>, keysCollected: Set<Char>): Boolean {
        return blockers.filter { !keysCollected.contains(it) }.isEmpty()
    }

    private fun hash(currentKeys: CharArray, keysCollected: Set<Char>): String {
        return "${currentKeys.sorted().joinToString(".")}-${keysCollected.sorted().joinToString(".")}"
    }

    private fun findPaths(
        startPosition: Pair<Int, Int>
    ): List<PathToKey> {
        val foundPaths = ArrayList<PathToKey>().toMutableList()
        val blockerPattern = Regex("[A-Z]")
        val keyPattern = Regex("[a-z]")
        val explorations = ArrayDeque<PathState>() // Breadth first
        val visitedPositions = HashSet<Pair<Int, Int>>()
        visitedPositions.add(startPosition)
        explorations.add(PathState(startPosition, 0, Collections.emptyList()))
        while (explorations.isNotEmpty() && foundPaths.size < numberOfKeys) {
            val pathState = explorations.poll()!!
            val currentPosition = pathState.position
            for (dir in Arrays.asList(Pair(0, -1), Pair(0, 1), Pair(-1, 0), Pair(1, 0))) {
                val newPosition = Pair(currentPosition.first + dir.first, currentPosition.second + dir.second)
                if (visitedPositions.contains(newPosition)) {
                    continue
                }
                val tile = map[newPosition.second][newPosition.first]
                if (tile == '#') {
                    continue
                }
                val blockers = pathState.blockers.toMutableList()
                if (tile.toString().matches(blockerPattern)) {
                    blockers.add(tile.toLowerCase())
                }
                if (tile.toString().matches(keyPattern)) {
                    foundPaths.add(PathToKey(tile, pathState.steps + 1, blockers))
                }
                visitedPositions.add(newPosition)
                explorations.add(PathState(newPosition, pathState.steps + 1, blockers))
            }
        }
        return foundPaths
    }

    data class PathToKey(val to: Char, val distance: Int, val blockedBy: List<Char>)

    data class PathState(
        val position: Pair<Int, Int>,
        val steps: Int,
        val blockers: List<Char>
    )

}