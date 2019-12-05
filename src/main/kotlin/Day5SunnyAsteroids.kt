class Day5SunnyAsteroids {
    fun programResult(program: IntArray, input: Int): Int {
        var currentPosition = 0
        var output = 0
        while (currentPosition < program.size) {
            val opCodeString = String.format("%05d", program[currentPosition])
            val currentOperation = opCodeString.substring(3)

            if (currentOperation == "99") {
                return output
            }

            if (currentOperation == "03") {
                program[program[currentPosition + 1]] = input
                currentPosition += 2
                continue
            }

            if (currentOperation == "04") {
                output = program[program[currentPosition + 1]]
                currentPosition += 2
                continue
            }

            val resultPos = program[currentPosition + 3]
            val param1 =
                if (opCodeString[2] == '1') program[currentPosition + 1] else program[program[currentPosition + 1]]
            val param2 =
                if (opCodeString[1] == '1') program[currentPosition + 2] else program[program[currentPosition + 2]]

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