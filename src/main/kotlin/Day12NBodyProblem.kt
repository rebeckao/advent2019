import Util.Companion.greatestCommonDivisorPreservingSign
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

    fun totalEnergyDecoupled(moonsStrings: List<String>, steps: Int): Int {
        val moons = moonsStrings.map { Moon(it) }.toSet()
        val xStateAfterSteps = stateAfterSteps(moons.map { it.position[0] }.toIntArray(), steps)
        val yStateAfterSteps = stateAfterSteps(moons.map { it.position[1] }.toIntArray(), steps)
        val zStateAfterSteps = stateAfterSteps(moons.map { it.position[2] }.toIntArray(), steps)
        var sum = 0
        for (i in 0..3) {
            val potential =
                abs(xStateAfterSteps.first[i]) + abs(yStateAfterSteps.first[i]) + abs(zStateAfterSteps.first[i])
            val kinetic =
                abs(xStateAfterSteps.second[i]) + abs(yStateAfterSteps.second[i]) + abs(zStateAfterSteps.second[i])
            sum += potential * kinetic
        }
        return sum
    }

    private fun stateAfterSteps(positions: IntArray, steps: Int): Pair<IntArray, IntArray> {
        val velocities = intArrayOf(0, 0, 0, 0)
        val states = HashSet<String>().toMutableSet()
        states.add(hash(Pair(positions, velocities)))
        for (step in 0 until steps) {
            updateVelocitiesAndPositions(velocities, positions)
        }
        return Pair(positions, velocities)
    }

    fun stepsToRepeatDecoupled(moonsStrings: List<String>): Long {
        val moons = moonsStrings.map { Moon(it) }.toList()
        val stepsToRepeatForX =
            stepsToRepeatForDimension(moons.map { it.position[0] }.toIntArray(), intArrayOf(0, 0, 0, 0))
        println("stepsToRepeatForX = ${stepsToRepeatForX}")
        val stepsToRepeatForY =
            stepsToRepeatForDimension(moons.map { it.position[1] }.toIntArray(), intArrayOf(0, 0, 0, 0))
        println("stepsToRepeatForY = ${stepsToRepeatForY}")
        val stepsToRepeatForZ =
            stepsToRepeatForDimension(moons.map { it.position[2] }.toIntArray(), intArrayOf(0, 0, 0, 0))
        println("stepsToRepeatForZ = ${stepsToRepeatForZ}")
        val xAndY = stepsToRepeatForX * stepsToRepeatForY /
                greatestCommonDivisorPreservingSign(stepsToRepeatForX, stepsToRepeatForY)
        val xAndYAndZ = xAndY * stepsToRepeatForZ / greatestCommonDivisorPreservingSign(xAndY, stepsToRepeatForZ)
        return xAndYAndZ
    }

    private fun stepsToRepeatForDimension(positions: IntArray, velocities: IntArray): Long {
        val states = HashSet<String>().toMutableSet()
        states.add(hash(Pair(positions, velocities)))
        for (step in 1 until Long.MAX_VALUE) {
            updateVelocitiesAndPositions(velocities, positions)
            val hash = hash(Pair(positions, velocities))
            if (states.contains(hash)) {
                return step
            }
            states.add(hash)
        }
        throw IllegalStateException()
    }

    private fun updateVelocitiesAndPositions(velocities: IntArray, positions: IntArray) {
        for (i in velocities.indices) {
            velocities[i] += positions.map { it.compareTo(positions[i]) }.sum()
        }
        for (i in positions.indices) {
            positions[i] += velocities[i]
        }
    }

    private fun hash(state: Pair<IntArray, IntArray>) =
        "" + state.first.contentHashCode() + state.second.contentHashCode()

    data class Moon(var position: IntArray, var velocity: IntArray) {

        constructor(str: String) : this(intArrayOf(0, 0, 0), intArrayOf(0, 0, 0)) {
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
                .filter { !it!!.contentEquals(position) }
                .map { it[0] }
                .mapToInt { it.compareTo(position[0]) }
                .sum()
            val yDelta = positions.stream()
                .filter { !it!!.contentEquals(position) }
                .map { it[1] }
                .mapToInt { it.compareTo(position[1]) }
                .sum()
            val zDelta = positions.stream()
                .filter { !it!!.contentEquals(position) }
                .map { it[2] }
                .mapToInt { it.compareTo(position[2]) }
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