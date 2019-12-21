import java.util.*

class Day19TractorBeam {
    fun pointsAffectedByTractorBeam(program: LongArray): Int {
        var affectedPoints = 0
        for (x in 0..49) {
            for (y in 0..49) {
                val intComputer = IntComputer(program)
                val input = ArrayDeque<Long>()
                input.add(x.toLong())
                input.add(y.toLong())
                affectedPoints += intComputer.nextOutput(input).output?.toInt() ?: 0
            }
        }
        return affectedPoints
    }
}