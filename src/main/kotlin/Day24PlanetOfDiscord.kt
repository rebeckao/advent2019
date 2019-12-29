import kotlin.math.pow

class Day24PlanetOfDiscord(initialState: List<String>) {
    private var state: MutableList<MutableList<Boolean>> = ArrayList<MutableList<Boolean>>().toMutableList()

    init {
        for (y in initialState.indices) {
            val rowList = ArrayList<Boolean>()
            state.add(rowList)
            val row = initialState[y]
            for (x in row.indices) {
                rowList.add(row[x] == '#')
            }
        }
    }

    fun biodiversityOfFirstRepeatedState(): Long {
        val possibleDirections = listOf(Pair(0, -1), Pair(0, 1), Pair(-1, 0), Pair(1, 0))
        val visitedStates = HashSet<String>()
        while (true) {
            draw()
            val newState: MutableList<MutableList<Boolean>> = state.map { it.toMutableList() }.toMutableList()
            for (y in newState.indices) {
                for (x in newState[y].indices) {
                    val surroundingBugs = possibleDirections.filter { isBug(x + it.first, y + it.second) }.count()
                    if (isBug(x, y)) {
                        newState[y][x] = surroundingBugs == 1
                    } else {
                        newState[y][x] = surroundingBugs == 1 || surroundingBugs == 2
                    }
                }
            }
            val hash = hash(newState)
            if (visitedStates.contains(hash)) {
                draw()
                return biodiversityOf(newState)
            }
            visitedStates.add(hash)
            state = newState
        }
    }

    private fun draw() {
        for (y in state.indices) {
            val row = state[y]
            for (x in row) {
                if (x) print('#') else print('.')
            }
            println()
        }
        println()
    }

    private fun biodiversityOf(state: List<List<Boolean>>): Long {
        val asOneList = state.flatten()
        return asOneList.indices
            .filter { asOneList[it] }
            .map { it.toDouble() }
            .map { 2.0.pow(it) }
            .sum()
            .toLong()
    }

    private fun hash(state: List<List<Boolean>>): String {
        return state.joinToString("") { it.joinToString("") }
    }

    private fun isBug(x: Int, y: Int): Boolean {
        if (y < 0 || y >= state.size) {
            return false
        }
        if (x < 0 || x >= state[y].size) {
            return false
        }
        return state[y][x]
    }
}