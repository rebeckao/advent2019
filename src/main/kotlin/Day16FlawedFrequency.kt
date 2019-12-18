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

    fun decodedFromTheEnd(initialInput: String, phases: Int) : String {
        val offsetIndex = initialInput.substring(0, 7).toInt()
        val shortInput = initialInput
            .split("")
            .filter { it != "" }
            .map { it.toInt() }
            .toIntArray()

        val relevantLength = initialInput.length * 10000 - offsetIndex
        val input = IntArray(relevantLength)
        for (index in 0 until relevantLength) {
            input[index] = shortInput[(index + offsetIndex) % shortInput.size]
        }

        for (phase in 0 until phases) {
            for (outputDigitNumber in relevantLength - 2 downTo 0) {
                input[outputDigitNumber] = (input[outputDigitNumber] + input[outputDigitNumber + 1]) % 10
            }
        }
        return input.toList().subList(0, 8).joinToString("")
    }
}