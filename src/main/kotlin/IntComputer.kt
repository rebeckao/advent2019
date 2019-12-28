import java.util.*

class IntComputer(private var program: LongArray) {
    private var currentPosition = 0
    private var relativeBase = 0

    fun nextOutput(
        input: Queue<Long>
    ): CurrentState {
        return nextOutputWithProvider({ input.poll() })
    }

    fun nextOutputWithProvider(
        inputProducer: () -> Long
    ): CurrentState {
        while (currentPosition < program.size) {
            val result = nextInstruction(inputProducer)
            if (result != null) {
                return result
            }
        }
        throw IllegalStateException("No output and no halt")
    }

    fun nextInstruction(
        inputProducer: () -> Long
    ): CurrentState? {
        val opCodeString = String.format("%05d", program[currentPosition])
        val currentOperation = opCodeString.substring(3)

        if (currentOperation == "99") {
            return CurrentState(null, true)
        }

        if (currentOperation == "03") {
            val resultPos = resolveResultPos(currentPosition + 1, opCodeString[2], relativeBase)
            assignOrIncrease(resultPos, inputProducer())
            currentPosition += 2
            return null
        }

        val param1 = resolveParamValue(opCodeString[2], getOrIncrease(currentPosition + 1), relativeBase)

        if (currentOperation == "04") {
            currentPosition += 2
            return CurrentState(param1, false)
        }

        if (currentOperation == "09") {
            relativeBase = (relativeBase + param1).toInt()
            currentPosition += 2
            return null
        }

        val param2 = resolveParamValue(opCodeString[1], getOrIncrease(currentPosition + 2), relativeBase)

        if (currentOperation == "05") {
            if (param1 != 0L) {
                currentPosition = param2.toInt()
            } else {
                currentPosition += 3
            }
            return null
        }

        if (currentOperation == "06") {
            if (param1 == 0L) {
                currentPosition = param2.toInt()
            } else {
                currentPosition += 3
            }
            return null
        }

        val resultPos = resolveResultPos(currentPosition + 3, opCodeString[0], relativeBase)

        if (currentOperation == "07") {
            assignOrIncrease(resultPos, if (param1 < param2) 1L else 0L)
            currentPosition += 4
            return null
        }

        if (currentOperation == "08") {
            assignOrIncrease(resultPos, if (param1 == param2) 1L else 0L)
            currentPosition += 4
            return null
        }

        if (currentOperation == "01") {
            assignOrIncrease(resultPos, param1 + param2)
            currentPosition += 4
            return null
        }

        if (currentOperation == "02") {
            assignOrIncrease(resultPos, param1 * param2)
            currentPosition += 4
            return null
        }

        throw IllegalStateException("op code = ${currentOperation}, at position $currentPosition")
    }


    private fun resolveResultPos(
        paramPosition: Int,
        opCodeParamType: Char,
        relativeBase: Int
    ): Int {
        val param = getOrIncrease(paramPosition).toInt()
        val resultPos = when {
            opCodeParamType == '0' -> param
            opCodeParamType == '2' -> param + relativeBase
            else -> throw IllegalStateException()
        }
        return resultPos
    }

    private fun resolveParamValue(
        opCodeParamType: Char,
        param: Long,
        relativeBase: Int
    ): Long {
        when {
            opCodeParamType == '0' -> return getOrIncrease(param.toInt())
            opCodeParamType == '1' -> return param
            opCodeParamType == '2' -> return getOrIncrease((param + relativeBase).toInt())
            else -> throw IllegalStateException()
        }
    }

    private fun getOrIncrease(index: Int): Long {
        increase(index)
        return program[index]
    }

    private fun assignOrIncrease(index: Int, value: Long) {
        increase(index)
        program[index] = value
    }

    private fun increase(index: Int) {
        if (index >= program.size) {
            val increase = index - program.size + 1
            program += LongArray(increase)
        }
    }

    data class CurrentState(val output: Long?, val done: Boolean)
}