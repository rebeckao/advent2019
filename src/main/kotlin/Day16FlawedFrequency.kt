
import kotlin.math.abs

class Day16FlawedFrequency {
    fun firstEightDigitsInFinalOutput(initialInput: String, phases: Int): String {
        val basePattern = listOf(0, 1, 0, -1)
        var input = initialInput
            .split("")
            .filter { it != "" }
            .map { it.toInt() }
        for (phase in 0 until phases) {
            val output = ArrayList<Int>()
            for (outPutDigitNumber in 1..input.size) {
                val outputDigit = abs(input.indices
                    .map { basePattern[((it + 1) / outPutDigitNumber) % basePattern.size] * input[it] }
                    .sum()) % 10
                output.add(outputDigit)
            }
            input = output
        }
        return input.joinToString("").substring(0, 8)
    }
}