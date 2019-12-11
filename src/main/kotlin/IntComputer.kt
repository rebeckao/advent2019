import java.util.*

class IntComputer(private var program: LongArray) {

    fun nextOutput(
        input: Queue<Long>,
        startPosition: Int,
        relativeBaseStart: Int
    ): CurrentState {
        var relativeBase = relativeBaseStart
        var currentPosition = startPosition
        while (currentPosition < program.size) {
            val opCodeString = String.format("%05d", program[currentPosition])
            val currentOperation = opCodeString.substring(3)

            if (currentOperation == "99") {
                return CurrentState(null, currentPosition, relativeBase, true)
            }

            if (currentOperation == "03") {
//                val resultPos =
//                    resolveParamValue(opCodeString[2], getOrIncrease(currentPosition + 1), relativeBase)
//                assignOrIncrease(resultPos.toInt(), input.poll())
                assignOrIncrease(getOrIncrease(currentPosition + 1).toInt(), input.poll())
                currentPosition += 2
                continue
            }

            val param1 = resolveParamValue(opCodeString[2], getOrIncrease(currentPosition + 1), relativeBase)

            if (currentOperation == "04") {
                currentPosition += 2
                return CurrentState(param1, currentPosition, relativeBase, false)
            }

            if (currentOperation == "09") {
                relativeBase = (relativeBase + param1).toInt()
                currentPosition += 2
                continue
            }

            val param2 = resolveParamValue(opCodeString[1], getOrIncrease(currentPosition + 2), relativeBase)

            if (currentOperation == "05") {
                if (param1 != 0L) {
                    currentPosition = param2.toInt()
                } else {
                    currentPosition += 3
                }
                continue
            }

            if (currentOperation == "06") {
                if (param1 == 0L) {
                    currentPosition = param2.toInt()
                } else {
                    currentPosition += 3
                }
                continue
            }

            val resultPos = getOrIncrease(currentPosition + 3).toInt()

            if (currentOperation == "07") {
                assignOrIncrease(resultPos, if (param1 < param2) 1L else 0L)
                currentPosition += 4
                continue
            }

            if (currentOperation == "08") {
                assignOrIncrease(resultPos, if (param1 == param2) 1L else 0L)
                currentPosition += 4
                continue
            }

            if (currentOperation == "01") {
                assignOrIncrease(resultPos, param1 + param2)
                currentPosition += 4
                continue
            }

            if (currentOperation == "02") {
                assignOrIncrease(resultPos, param1 * param2)
                currentPosition += 4
                continue
            }

            throw IllegalStateException("op code = ${currentOperation}, at position $currentPosition")
        }
        throw IllegalStateException("No output and no halt")
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

    private fun getOrIncrease(index: Int) : Long {
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
            println("increase = ${increase}")
            program += LongArray(increase)
        }
    }

    data class CurrentState(val output: Long?, val position: Int, val relativeBase: Int, val done: Boolean)
}