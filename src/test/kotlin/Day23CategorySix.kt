import java.util.*

class Day23CategorySix(val program: LongArray) {
    fun firstPacketSentTo(targetAddress: Long): Long {
        val networkComputers = (0 until 50).map { IntComputer(program.clone()) }
        val inputs: List<Queue<Long>> = (0 until 50)
            .map {
                val input = ArrayDeque<Long>()
                input.add(it.toLong())
                input.add(-1)
                input
            }.toList()
        while (true) {
            (49 downTo 0).forEach {
                val currentInput = inputs[it]
                val currentComputer = networkComputers[it]
                val resultAfterNextInstruction = currentComputer.nextInstruction { inputOrDefault(currentInput) }
                if (resultAfterNextInstruction != null) {
                    val address = resultAfterNextInstruction.output!!
                    val x = currentComputer.nextOutputWithProvider { inputOrDefault(currentInput) }.output!!
                    val y = currentComputer.nextOutputWithProvider { inputOrDefault(currentInput) }.output!!
                    if (address == targetAddress) {
                        return y
                    }
                    val receiver = inputs[address.toInt()]
                    receiver.add(x)
                    receiver.add(y)
                }
            }
        }
    }

    private fun inputOrDefault(currentInput: Queue<Long>): Long {
        return if (currentInput.isEmpty()) {
            -1
        } else {
            currentInput.poll()
        }
    }

}