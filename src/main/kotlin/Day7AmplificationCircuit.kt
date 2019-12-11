import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max

class Day7AmplificationCircuit {
    fun maxThrusterSignal(program: LongArray): Int {
        val phaseSettings: List<IntArray> = possiblePhaseSettings(setOf(0, 1, 2, 3, 4))
        var maxOutput = 0L
        for (phaseSetting in phaseSettings) {
            var output = 0L
            for (phase in phaseSetting) {
                val inputQueue = ArrayDeque<Long>()
                inputQueue.add(phase.toLong())
                inputQueue.add(output)
                output = IntComputer(program.clone()).nextOutput(inputQueue, 0, 0).output!!
            }
            maxOutput = max(maxOutput, output)
        }
        return maxOutput.toInt()
    }

    fun maxThrusterSignalWithFeedback(program: LongArray): Int {
        val phaseSettings: List<IntArray> = possiblePhaseSettings(setOf(5, 6, 7, 8, 9))
        var maxOutput = 0
        for (phaseSetting in phaseSettings) {
            val output = maxThrusterSignalWithFeedback(program.clone(), phaseSetting)
            maxOutput = max(maxOutput, output)
        }
        return maxOutput
    }

    private fun maxThrusterSignalWithFeedback(program: LongArray, phaseSettings: IntArray): Int {
        val intComputers = IntRange(0, 4)
            .map{IntComputer(program.clone())}
        val inputQueues: List<Queue<Long>> = phaseSettings.asSequence().map{toQueue(it)}.toList()
        val positions = intArrayOf(0, 0, 0, 0, 0)
        var output = 0
        while (true) {
            for (i in intComputers.indices) {
                inputQueues[i].add(output.toLong())
                val nextOutput = intComputers[i].nextOutput(inputQueues[i], positions[i], 0)
                if (nextOutput.done) {
                    return output
                } else {
                    output = nextOutput.output!!.toInt()
                    positions[i] = nextOutput.position
                }
            }
        }
    }

    private fun toQueue(value: Int): ArrayDeque<Long> {
        val queue = ArrayDeque<Long>()
        queue.add(value.toLong())
        return queue
    }

    private fun possiblePhaseSettings(possibleSettings: Set<Int>): MutableList<IntArray> {
        val phaseSettings: MutableList<IntArray> = ArrayList()
        for (phase1 in possibleSettings) {
            for (phase2 in possibleSettings) {
                if (phase2 == phase1) continue
                for (phase3 in possibleSettings) {
                    if (phase3 == phase1 || phase3 == phase2) continue
                    for (phase4 in possibleSettings) {
                        if (phase4 == phase1 || phase4 == phase2 || phase4 == phase3) continue
                        for (phase5 in possibleSettings) {
                            if (phase5 == phase1 || phase5 == phase2 || phase5 == phase3 || phase5 == phase4) continue
                            phaseSettings.add(intArrayOf(phase1, phase2, phase3, phase4, phase5))
                        }
                    }
                }
            }
        }
        return phaseSettings
    }
}