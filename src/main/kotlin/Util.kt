import java.util.*
import java.util.stream.Stream
import kotlin.math.abs

class Util {
    companion object {
        fun toLongArray(program: String) = program.split(",").stream().mapToLong { it.toLong() }.toArray()

        fun toLongArray(lines: Stream<String>): LongArray {
            val programArray = lines
                .flatMap { it.split(",").stream() }
                .mapToLong { it.toLong() }
                .toArray()
            return programArray
        }

        fun toQueue(value: Long): Queue<Long> {
            val input = ArrayDeque<Long>()
            input.add(value)
            return input
        }

        fun greatestCommonDivisorPreservingSign(a: Long, b: Long): Long {
            return if (b == 0L) abs(a) else greatestCommonDivisorPreservingSign(b, a % b)
        }
    }
}