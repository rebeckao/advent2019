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
            if (currentOperation == 1) {
                program[resultPos] = param1 + param2
            } else if (currentOperation == 2) {
                program[resultPos] = param1 * param2
            } else {
                throw java.lang.IllegalStateException("op code = ${currentOperation}, at position $currentPosition")
            }
            currentPosition += 4
        }
        throw IllegalStateException()
    }
}