import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class Day18ManyWorlds(val map: List<String>) {
    val paths: Map<Char, List<PathToKey>>
    private val shortestPaths = HashMap<String, Int>().toMutableMap()

    init {
        this.paths = toPaths(map)
    }

    fun shortestPathToCollectAllKeys(): Int {
        val allKeyNodes = HashMap<Char, KeyNode>().toMutableMap()
        paths.filter { it.key != '@' }
            .map { KeyNode(it.key, it.value, shortestPaths) }
            .forEach { allKeyNodes[it.key] = it }
        val shortestPathToRemainingKeys =
            KeyNode('@', paths['@']!!, shortestPaths).shortestPathToRemainingKeys(emptySet(), allKeyNodes)
        return shortestPathToRemainingKeys
    }

    private fun toPaths(initialMap: List<String>): Map<Char, List<PathToKey>> {
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
        val map = initialMap.map { it.replace('@', '.') }
        val paths = HashMap<Char, List<PathToKey>>().toMutableMap()
        paths['@'] = findPaths('@', map, startingPosition, allKeys.keys)
        for (key in allKeys.keys) {
            paths[key] = findPaths(key, map, allKeys[key]!!, allKeys.keys)
        }
        return paths
    }

    private fun findPaths(
        startingKey: Char,
        initialMap: List<String>,
        startPosition: Pair<Int, Int>,
        allKeys: MutableSet<Char>
    ): List<PathToKey> {
        val foundPaths = ArrayList<PathToKey>().toMutableList()
        val keysToFind = allKeys.filter { it != startingKey }
        val blockerPattern = Regex("[A-Z]")
        val keyPattern = Regex("[a-z]")
        val explorations = ArrayDeque<PathState>() // Breadth first
        val visitedPositions = HashSet<Pair<Int, Int>>()
        visitedPositions.add(startPosition)
        explorations.add(PathState(startPosition, 0, Collections.emptyList()))
        while (explorations.isNotEmpty() && foundPaths.size < keysToFind.size) {
            val pathState = explorations.poll()!!
            val currentPosition = pathState.position
//            val currentTile = initialMap[currentPosition.second][currentPosition.first]
//            if (currentTile.toString().matches(keyPattern)) {
//                foundPaths.add(PathToKey(currentTile, pathState.steps, pathState.blockers))
//            }
            for (dir in Arrays.asList(Pair(0, -1), Pair(0, 1), Pair(-1, 0), Pair(1, 0))) {
                val newPosition = Pair(currentPosition.first + dir.first, currentPosition.second + dir.second)
                if (visitedPositions.contains(newPosition)) {
                    continue
                }
                val tile = initialMap[newPosition.second][newPosition.first]
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

    class KeyNode(
        val key: Char,
        private val pathsAway: List<PathToKey>,
        private val shortestPaths: MutableMap<String, Int>
    ) {

        fun shortestPathToRemainingKeys(keysCollectedIncoming: Set<Char>, allKeyNodes: Map<Char, KeyNode>): Int {
            val keysCollected = keysCollectedIncoming.toMutableSet()
            if (key != '@') {
                keysCollected.add(key)
            }
            if (keysCollected.size == allKeyNodes.size) {
                return 0
            }
            val hash = hash(key, keysCollected)
            if (!shortestPaths.containsKey(hash)) {
                val shortestPath = pathsAway
                    .filter { !keysCollected.contains(it.to) }
                    .filter { theWayIsCleared(it.blockedBy, keysCollected) }
                    .map { it.distance + allKeyNodes[it.to]!!.shortestPathToRemainingKeys(keysCollected, allKeyNodes) }
                    .min()!!
                shortestPaths[hash] = shortestPath
            }
            val shortest = shortestPaths[hash]!!
            return shortest
        }

        private fun theWayIsCleared(blockers: List<Char>, keysCollected: Set<Char>): Boolean {
            return blockers.filter { !keysCollected.contains(it) }.isEmpty()
        }

        private fun hash(key: Char, keysCollected: Set<Char>): String {
            return "$key-${keysCollected.sorted().joinToString(".")}"
        }
    }

}