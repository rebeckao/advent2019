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
        val moons = moonsStrings.map { Moon(it) }.toSet()
        val states = HashSet<String>().toMutableSet()
        states.add(hash(moons))
        for (step in 1 until Long.MAX_VALUE) {
            moons.forEach { it.updateVelocity(moons.map { it.position }) }
            moons.forEach { it.updatePosition() }
            val hash = hash(moons)
            if (states.contains(hash)) {
                return step
            }
            states.add(hash)
        }
        return 0
    }

    private fun hash(moons: Set<Moon>) =
        moons.map { it.hashCode() }.joinToString("")

    data class Moon(var position: threeDVector, var velocity: threeDVector) {

        constructor(str: String) : this(threeDVector(0, 0, 0), threeDVector(0, 0, 0)) {
            this.position = parsePosition(str)
        }

        private fun parsePosition(str: String): threeDVector {
            val vectorPattern = Regex("<x=(.*), y=(.*), z=(.*)>")
            val match = vectorPattern.matchEntire(str)
            val x = match!!.groupValues[1].toInt()
            val y = match.groupValues[2].toInt()
            val z = match.groupValues[3].toInt()
            return threeDVector(x, y, z)
        }

        fun kineticEnergy(): Int {
            return abs(velocity.x) + abs(velocity.y) + abs(velocity.z)
        }

        fun potentialEnergy(): Int {
            return abs(position.x) + abs(position.y) + abs(position.z)
        }

        fun updateVelocity(positions: List<threeDVector>) {
            val xDelta = positions.stream()
                .filter{it != position}
                .map { it.x }
                .map { it - position.x }
                .mapToInt { if (it == 0) 0 else it / abs(it) }
                .sum()
            val yDelta = positions.stream()
                .filter{it != position}
                .map { it.y }
                .map { it - position.y }
                .mapToInt { if (it == 0) 0 else it / abs(it) }
                .sum()
            val zDelta = positions.stream()
                .filter{it != position}
                .map { it.z }
                .map { it - position.z }
                .mapToInt { if (it == 0) 0 else it / abs(it) }
                .sum()
            velocity.x += xDelta
            velocity.y += yDelta
            velocity.z += zDelta
        }

        fun updatePosition() {
            position.x += velocity.x
            position.y += velocity.y
            position.z += velocity.z
        }
    }

    data class threeDVector(var x: Int, var y: Int, var z: Int)
}