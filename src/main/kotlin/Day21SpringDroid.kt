import java.util.*

class Day21SpringDroid(val program: LongArray) {
    fun amountOfHullDamageReportedByDroid(instructions: List<String>): Long {
        val intComputer = IntComputer(program)
        val input = ArrayDeque<Long>()

        for (instruction in instructions) {
            for (c in instruction) {
                input.add(c.toLong())
            }
            input.add(10)
        }

        var output = intComputer.nextOutput(input)
        while (!output.done) {
            val outputValue = output.output
            if (outputValue!! > 255L) {
                println("output = $outputValue")
                return outputValue
            } else {
                print(outputValue.toChar())

            }
            output = intComputer.nextOutput(input)
        }
        return 0
    }
}