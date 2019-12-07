import java.util.*

class Day5SunnyAsteroids {
    fun programResult(program: IntArray, input: Queue<Int>): Int {
        var currentPosition = 0
        var output = 0
        while (currentPosition < program.size) {
            val opCodeString = String.format("%05d", program[currentPosition])
            val currentOperation = opCodeString.substring(3)

            if (currentOperation == "99") {
                return output
            }

            if (currentOperation == "03") {
                program[program[currentPosition + 1]] = input.poll()
                currentPosition += 2
                continue
            }

            val param1 =
                if (opCodeString[2] == '1') program[currentPosition + 1] else program[program[currentPosition + 1]]

            if (currentOperation == "04") {
                output = param1
                currentPosition += 2
                continue
            }

            val param2 =
                if (opCodeString[1] == '1') program[currentPosition + 2] else program[program[currentPosition + 2]]

            if (currentOperation == "05") {
                if (param1 != 0) {
                    currentPosition = param2
                } else {
                    currentPosition += 3
                }
                continue
            }

            if (currentOperation == "06") {
                if (param1 == 0) {
                    currentPosition = param2
                } else {
                    currentPosition += 3
                }
                continue
            }

            val resultPos = program[currentPosition + 3]

            if (currentOperation == "07") {
                program[resultPos] = if (param1 < param2) 1 else 0
                currentPosition += 4
                continue
            }

            if (currentOperation == "08") {
                program[resultPos] = if (param1 == param2) 1 else 0
                currentPosition += 4
                continue
            }

            if (currentOperation == "01") {
                program[resultPos] = param1 + param2
                currentPosition += 4
                continue
            }

            if (currentOperation == "02") {
                program[resultPos] = param1 * param2
                currentPosition += 4
                continue
            }

            throw IllegalStateException("op code = ${currentOperation}, at position $currentPosition")
        }
        throw IllegalStateException()
    }
}