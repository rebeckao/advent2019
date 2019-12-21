
import java.util.*

class Day9SensorBoost {
    fun resultOfIntComputer(program: LongArray, input : Queue<Long> ) : List<Long> {
        val outputs = ArrayList<Long>()
        val intComputer = IntComputer(program)
        var currentOutput = intComputer.nextOutput(input)
        while (!currentOutput.done) {
            outputs.add(currentOutput.output!!)
            currentOutput = intComputer.nextOutput(input)
        }
        return outputs
    }
}