val UP = Pair(0, -1)
val DOWN = Pair(0, 1)
val LEFT = Pair(-1, 0)
val RIGHT = Pair(1, 0)

class Day24PlanetOfDiscordPart2(initialState: List<String>, private val minutes: Int) {
    private val possibleDirections = listOf(UP, DOWN, LEFT, RIGHT)
    private var state: Array<Array<BooleanArray>>
    private val activeLayers: MutableSet<Int>

    init {
        val startingLayer = minutes + 1
        state = Array(startingLayer * 2) { Array(5) { BooleanArray(5) { false } } }
        activeLayers = setOf(startingLayer).toMutableSet()
        for (y in initialState.indices) {
            val row = initialState[y]
            for (x in row.indices) {
                state[startingLayer][y][x] = row[x] == '#'
            }
        }
    }

    fun bugsPresentInRecursiveGridAfterMinutes(): Int {
        for (minute in 0 until minutes) {
            val newState: Array<Array<BooleanArray>> = Array(state.size) { Array(5) { BooleanArray(5) { false } } }
            for (layer in (activeLayers.min()!! - 1)..(activeLayers.max()!! + 1)) {
                for (y in 0 until 5) {
                    for (x in 0 until 5) {
                        if (x == 2 && y == 2) {
                            continue
                        }
                        val surroundingBugs = possibleDirections
                            .map { numberOfBugs(layer, x + it.first, y + it.second, it) }
                            .sum()
                        val isBug = state[layer][y][x]
                        val shouldBeBug = (isBug && surroundingBugs == 1)
                                || (!isBug && (surroundingBugs == 1 || surroundingBugs == 2))
                        if (shouldBeBug) {
                            activeLayers.add(layer)
                        }
                        newState[layer][y][x] = shouldBeBug
                    }
                }
            }
            state = newState
        }
        return state
            .map { layer ->
                layer
                    .map { row ->
                        row
                            .count { it }
                    }
                    .sum()
            }
            .sum()
    }

    private fun numberOfBugs(layer: Int, x: Int, y: Int, direction: Pair<Int, Int>): Int {
        // Outer layer
        if (y < 0) {
            return if (state[layer - 1][1][2]) 1 else 0
        }
        if (y > 4) {
            return if (state[layer - 1][3][2]) 1 else 0
        }
        if (x < 0) {
            return if (state[layer - 1][2][1]) 1 else 0
        }
        if (x > 4) {
            return if (state[layer - 1][2][3]) 1 else 0
        }

        // Inner layer
        if (x == 2 && y == 2) {
            if (direction == UP) {
                return state[layer + 1][4].count { it }
            }
            if (direction == DOWN) {
                return state[layer + 1][0].count { it }
            }
            if (direction == LEFT) {
                return state[layer + 1].map { it[4] }.count { it }
            }
            if (direction == RIGHT) {
                return state[layer + 1].map { it[0] }.count { it }
            }
        }

        // Current layer
        return if (state[layer][y][x]) {
            1
        } else {
            0
        }
    }
}