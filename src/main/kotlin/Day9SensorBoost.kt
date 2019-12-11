
import java.util.*

class Day9SensorBoost {
    fun resultOfIntComputer(program: LongArray, input : Queue<Long> ) : List<Long> {
        val outputs = ArrayList<Long>()
        var currentPosition: Int
        var relativeBase: Int
        val intComputer = IntComputer(program)
        var currentOutput = intComputer.nextOutput(input, 0, 0)
        while (!currentOutput.done) {
            outputs.add(currentOutput.output!!)
            currentPosition = currentOutput.position
            relativeBase = currentOutput.relativeBase
            currentOutput = intComputer.nextOutput(input, currentPosition, relativeBase)
        }
        return outputs
    }
}