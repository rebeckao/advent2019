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