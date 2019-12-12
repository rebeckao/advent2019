import kotlin.math.abs

class Day12NBodyProblem {
    fun totalEnergy(moonsStrings: List<String>, steps: Int): Int {
        val moons = moonsStrings.map { Moon(it) }.toSet()
        for (step in 0 until steps) {
            moons.forEach { it.updateVelocity(moons.map { it.position }) }
            moons.forEach { it.updatePosition() }
        }
        return moons.stream().mapToInt { it.kineticEnergy() * it.potentialEnergy() }.sum()
    }

    fun stepsToRepeat(moonsStrings: List<String>): Long {
        val moons = moonsStrings.map { Moon(it) }.toList()
        val states = HashSet<String>().toMutableSet()
        states.add(hash(moons))
        val start = System.currentTimeMillis()
        for (step in 1 until Long.MAX_VALUE) {
            moons.forEach { it.updateVelocity(moons.map { it.position }) }
            moons.forEach { it.updatePosition() }
            val hash = hash(moons)
            if (states.contains(hash)) {
                return step
            }
            states.add(hash)
            if (step % 100_000L == 0L) {
                println("System.currentTimeMillis() = ${(System.currentTimeMillis() - start)}")
            }
        }
        return 0
    }

    private fun hash(moons: List<Moon>) =
        moons.map { it.hashCode() }.joinToString("")

    data class Moon(var position: IntArray, var velocity: IntArray) {

        constructor(str: String) : this(intArrayOf(0,0,0), intArrayOf(0,0,0)) {
            this.position = parsePosition(str)
        }

        private fun parsePosition(str: String): IntArray {
            val vectorPattern = Regex("<x=(.*), y=(.*), z=(.*)>")
            val match = vectorPattern.matchEntire(str)
            val x = match!!.groupValues[1].toInt()
            val y = match.groupValues[2].toInt()
            val z = match.groupValues[3].toInt()
            return intArrayOf(x, y, z)
        }

        fun kineticEnergy(): Int {
            return abs(velocity[0]) + abs(velocity[1]) + abs(velocity[2])
        }

        fun potentialEnergy(): Int {
            return abs(position[0]) + abs(position[1]) + abs(position[2])
        }

        fun updateVelocity(positions: List<IntArray>) {
            val xDelta = positions.stream()
                .filter{ !it!!.contentEquals(position) }
                .map { it[0] }
                .map { it - position[0] }
                .mapToInt { if (it == 0) 0 else it / abs(it) }
                .sum()
            val yDelta = positions.stream()
                .filter{ !it!!.contentEquals(position) }
                .map { it[1] }
                .map { it - position[1] }
                .mapToInt { if (it == 0) 0 else it / abs(it) }
                .sum()
            val zDelta = positions.stream()
                .filter{ !it!!.contentEquals(position) }
                .map { it[2] }
                .map { it - position[2] }
                .mapToInt { if (it == 0) 0 else it / abs(it) }
                .sum()
            velocity[0] += xDelta
            velocity[1] += yDelta
            velocity[2] += zDelta
        }

        fun updatePosition() {
            position[0] += velocity[0]
            position[1] += velocity[1]
            position[2] += velocity[2]
        }
    }
}