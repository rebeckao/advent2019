import java.lang.Math.max
import java.util.*
import kotlin.collections.ArrayList

class Day7AmplificationCircuit {
    fun maxThusterSignal(program: IntArray): Int {
        val phaseSettings: List<IntArray> = possiblePhaseSettings(setOf(0, 1, 2, 3, 4))
        var maxOutput = 0
        for (phaseSetting in phaseSettings) {
            var output = 0
            for (phase in phaseSetting) {
                val inputQueue = ArrayDeque<Int>()
                inputQueue.add(phase)
                inputQueue.add(output)
                output = Day5SunnyAsteroids().programResult(program.clone(), inputQueue)
            }
            maxOutput = max(maxOutput, output)
        }
        return maxOutput
    }

    fun maxThrusterSignalWithFeedback(program: IntArray): Int {
        val phaseSettings: List<IntArray> = possiblePhaseSettings(setOf(5, 6, 7, 8, 9))
        var maxOutput = 0
        for (phaseSetting in phaseSettings) {
            val output = maxThrusterSignalWithFeedback(program.clone(), phaseSetting)
            maxOutput = max(maxOutput, output)
        }
        return maxOutput
    }

    fun maxThrusterSignalWithFeedback(program: IntArray, phaseSettings: IntArray): Int {
        val programs: MutableList<IntArray> =
            mutableListOf(program.clone(), program.clone(), program.clone(), program.clone(), program.clone())
        val inputQueues: List<Queue<Int>> = phaseSettings.asSequence().map{toQueue(it)}.toList()
        val positions = intArrayOf(0, 0, 0, 0, 0)
        var output = 0
        while (true) {
            for (i in 0 until programs.size) {
                inputQueues[i].add(output)
                val nextOutput = nextOutput(programs[i], inputQueues[i], positions[i])
                if (nextOutput == null) {
                    return output
                } else {
                    output = nextOutput.first
                    positions[i] = nextOutput.second
                }
            }
        }
    }

    private fun toQueue(value: Int): ArrayDeque<Int> {
        val queue = ArrayDeque<Int>()
        queue.add(value)
        return queue
    }

    fun possiblePhaseSettings(possibleSettings: Set<Int>): MutableList<IntArray> {
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

    private fun nextOutput(program: IntArray, input: Queue<Int>, startPosition : Int): Pair<Int,Int>? {
        var currentPosition = startPosition
        while (currentPosition < program.size) {
            val opCodeString = String.format("%05d", program[currentPosition])
            val currentOperation = opCodeString.substring(3)

            if (currentOperation == "99") {
                return null
            }

            if (currentOperation == "03") {
                if (input.size == 0) {
                    println("currentPosition = ${currentPosition}")
                }
                program[program[currentPosition + 1]] = input.poll()
                currentPosition += 2
                continue
            }

            val param1 =
                if (opCodeString[2] == '1') program[currentPosition + 1] else program[program[currentPosition + 1]]

            if (currentOperation == "04") {
                currentPosition+=2
                return Pair(param1, currentPosition)
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
        throw IllegalStateException("No output and no halt")
    }
}