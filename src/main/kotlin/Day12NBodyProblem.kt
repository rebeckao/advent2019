import Util.Companion.greatestCommonDivisorPreservingSign
import kotlin.math.abs

class Day12NBodyProblem {

    fun totalEnergyDecoupled(moonsStrings: List<String>, steps: Int): Int {
        val moons = moonsStrings.map { parsePosition(it) }.toSet()
        val xStateAfterSteps = stateAfterSteps(moons.map { it[0] }.toIntArray(), steps)
        val yStateAfterSteps = stateAfterSteps(moons.map { it[1] }.toIntArray(), steps)
        val zStateAfterSteps = stateAfterSteps(moons.map { it[2] }.toIntArray(), steps)
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
        val moons = moonsStrings.map { parsePosition(it) }.toList()
        val stepsToRepeatForX = stepsToRepeatForDimension(moons.map { it[0] }.toIntArray())
        val stepsToRepeatForY = stepsToRepeatForDimension(moons.map { it[1] }.toIntArray())
        val stepsToRepeatForZ = stepsToRepeatForDimension(moons.map { it[2] }.toIntArray())
        val xAndY = stepsToRepeatForX * stepsToRepeatForY / greatestCommonDivisorPreservingSign(
            stepsToRepeatForX,
            stepsToRepeatForY
        )
        val xAndYAndZ = xAndY * stepsToRepeatForZ / greatestCommonDivisorPreservingSign(xAndY, stepsToRepeatForZ)
        return xAndYAndZ
    }

    fun stepsToRepeatForDimension(positions: IntArray): Long {
        val velocities = intArrayOf(0, 0, 0, 0)
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

    private fun parsePosition(str: String): IntArray {
        val vectorPattern = Regex("<x=(.*), y=(.*), z=(.*)>")
        val match = vectorPattern.matchEntire(str)
        val x = match!!.groupValues[1].toInt()
        val y = match.groupValues[2].toInt()
        val z = match.groupValues[3].toInt()
        return intArrayOf(x, y, z)
    }

    private fun hash(state: Pair<IntArray, IntArray>) =
        "" + state.first.joinToString("") + state.second.joinToString("")
}