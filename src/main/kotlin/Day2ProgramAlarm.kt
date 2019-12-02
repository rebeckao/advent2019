class Day2ProgramAlarm {
    fun programResult(program: IntArray) : Int {
        var currentPosition = 0;
        while(currentPosition < program.size) {
            val currentOperation = program[currentPosition]
            if (currentOperation == 99) {
                return program[0]
            }
            val resultPos = program[currentPosition + 3]
            val param1 = program[program[currentPosition + 1]]
            val param2 = program[program[currentPosition + 2]]
            when (currentOperation) {
                1 -> {
                    program[resultPos] = param1 + param2
                }
                2 -> {
                    program[resultPos] = param1 * param2
                }
                else -> {
                    throw IllegalStateException("op code = ${currentOperation}, at position $currentPosition")
                }
            }
            currentPosition += 4
        }
        throw IllegalStateException()
    }

    fun paramsRequiredForProgramResult(expectedResult: Int, program: IntArray): Int {
        for (noun in 0..99) {
            for (verb in 0..99) {
                val currentProgram = program.clone()
                currentProgram[1] = noun
                currentProgram[2] = verb
                if (programResult(currentProgram) == expectedResult) {
                    return 100*noun + verb
                }
            }
        }
        throw IllegalStateException()
    }
}