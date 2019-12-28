import java.util.*

class Day23CategorySix(val program: LongArray) {
    fun firstPacketSentTo255(): Long {
        val networkComputers = (0 until 50).map { IntComputer(program.clone()) }
        val inputs: List<Queue<Long>> = (0 until 50)
            .map {
                val input = ArrayDeque<Long>()
                input.add(it.toLong())
                input.add(-1)
                input
            }.toList()
        while (true) {
            (0 until 50).forEach {
                val currentInput = inputs[it]
                val currentComputer = networkComputers[it]
                val resultAfterNextInstruction = currentComputer.nextInstruction { inputOrDefault(currentInput) }
                if (resultAfterNextInstruction != null) {
                    val address = resultAfterNextInstruction.output!!
                    val x = currentComputer.nextOutputWithProvider { inputOrDefault(currentInput) }.output!!
                    val y = currentComputer.nextOutputWithProvider { inputOrDefault(currentInput) }.output!!
                    if (address == 255L) {
                        return y
                    }
                    val receiver = inputs[address.toInt()]
                    receiver.add(x)
                    receiver.add(y)
                }
            }
        }
    }

    fun firstPacketSentTwiceByNat(): Long {
        val networkComputers = (0 until 50).map { IntComputer(program.clone()) }
        val inputs: List<Queue<Long>> = (0 until 50)
            .map {
                val input = ArrayDeque<Long>()
                input.add(it.toLong())
                input.add(-1)
                input
            }.toList()
        var nat: Pair<Long, Long>? = null
        var lastDeliveredY = 0L
        while (true) {
            var anythingSent = false
            (0 until 50).forEach {
                val currentInput = inputs[it]
                val currentComputer = networkComputers[it]
                val resultAfterNextInstruction = currentComputer.nextInstruction { inputOrDefault(currentInput) }
                if (resultAfterNextInstruction != null) {
                    val address = resultAfterNextInstruction.output!!
                    val x = currentComputer.nextOutputWithProvider { inputOrDefault(currentInput) }.output!!
                    val y = currentComputer.nextOutputWithProvider { inputOrDefault(currentInput) }.output!!
                    if (address == 255L) {
                        nat = Pair(x, y)
                    } else {
                        val receiver = inputs[address.toInt()]
                        receiver.add(x)
                        receiver.add(y)
                    }
                    anythingSent = true
                }
            }
            if (!anythingSent && inputs.find { it.isNotEmpty() } == null && nat != null) {
                val natY = nat!!.second
                if (natY == lastDeliveredY) {
                    return natY
                }
                val natX = nat!!.first
                inputs[0].add(natX)
                inputs[0].add(natY)
                lastDeliveredY = natY
                nat = null
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