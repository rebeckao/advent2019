import java.util.*

class Day5SunnyAsteroids {
    fun programResult(program: LongArray, input: Queue<Long>): Long {
        val intComputer = IntComputer(program)
        var nextOutput = intComputer.nextOutput(input)
        var output = 0L
        while (!nextOutput.done) {
            output = nextOutput.output!!
            nextOutput = intComputer.nextOutput(input)
        }
        return output
    }
}