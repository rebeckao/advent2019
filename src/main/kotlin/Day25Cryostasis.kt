import Util.Companion.toLongArray
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
fun main(args: Array<String>) {
    val program = toLongArray(Files.lines(Paths.get("./src/test/resources/day25.txt")))
    Day25Cryostasis().controlDroid(program)
}
class Day25Cryostasis() {
    val currentInput = ArrayDeque<Long>()

    fun controlDroid(program: LongArray) {
        val intComputer = IntComputer(program)
        var output: Long? = intComputer.nextOutputWithProvider { manualInput() }.output
        while (output != null) {
            print(output.toChar())
            output = intComputer.nextOutputWithProvider { manualInput() }.output
        }
    }

    private fun manualInput(): Long {
        if (currentInput.isEmpty()) {
            val manualInstruction = readLine()!!
            for (c in manualInstruction) {
                currentInput.add(c.toLong())
            }
            currentInput.add(10)
        }
        return currentInput.poll()
    }
}