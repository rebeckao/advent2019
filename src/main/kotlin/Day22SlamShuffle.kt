import kotlin.math.roundToInt

class Day22SlamShuffle {
    fun cardIndexAfterShuffles(startIndex: Int, deckSize: Int, shuffles: List<String>): Int {
        var index = startIndex
        for (shuffle in shuffles) {
            val newIndex = indexAfterShuffle(index, deckSize, shuffle)
            index = newIndex
        }
        return index
    }

    private fun indexAfterShuffle(index: Int, deckSize: Int, shuffle: String): Int {
        when {
            shuffle == "deal into new stack" -> return deckSize - index - 1
            shuffle.startsWith("cut ") -> return cut(
                index.toLong(),
                deckSize.toLong(),
                shuffle.substring(4).toInt()
            ).toInt()
            shuffle.startsWith("deal with increment ") -> return dealWithIncrement(
                index,
                deckSize,
                shuffle.substring(20).toInt()
            )
        }
        throw IllegalStateException()
    }

    private fun cut(index: Long, deckSize: Long, numberToCut: Int): Long {
        return (index - numberToCut + deckSize) % deckSize
    }

    private fun dealWithIncrement(index: Int, deckSize: Int, increment: Int): Int {
        return (index * increment) % deckSize
    }

    fun cardIndexBeforeShuffles(
        indexAtEnd: Long,
        deckSize: Long,
        numberOfTimesToRepeat: Long,
        shuffles: List<String>
    ): Long {
        var index = indexAtEnd
        val reversedShuffles = shuffles.reversed()
        var i = numberOfTimesToRepeat
        val visitedShuffles = HashMap<Pair<Long, Long>, Long>()
        var shortcutTaken = false
        while (i > 0) {
            val startIndex = index
            for (shuffle in reversedShuffles) {
                val newIndex = indexAfterReverseShuffle(index, deckSize, shuffle)
                index = newIndex
            }
            val shuffleResult = Pair(startIndex, index)
            if (!shortcutTaken && visitedShuffles.containsKey(shuffleResult)) {
                val cycleLength = visitedShuffles[shuffleResult]!! - i
                val fullCyclesLeft = i / cycleLength
                println("skipping ${fullCyclesLeft} cycles of $cycleLength from $i")
                i -= fullCyclesLeft * cycleLength
                shortcutTaken = false
            } else {
                visitedShuffles.put(shuffleResult, i)
            }
            println("index: ${startIndex} -> ${index}, diff ${index - startIndex}")
            i--
        }
        return index
    }

    private fun indexAfterReverseShuffle(index: Long, deckSize: Long, shuffle: String): Long {
        when {
            shuffle == "deal into new stack" -> return deckSize - index - 1
            shuffle.startsWith("cut ") -> return cut(index, deckSize, -shuffle.substring(4).toInt())
            shuffle.startsWith("deal with increment ") -> return reverseDealWithIncrement(
                index,
                deckSize,
                shuffle.substring(20).toInt()
            )
        }
        throw IllegalStateException()
    }

    private fun reverseDealWithIncrement(index: Long, deckSize: Long, increment: Int): Long {
        val placeInBucket = index % increment
        val offsetBetweenRounds = deckSize % increment
        var round = 0
        for (a in 1 until increment) {
            val doubleRound = (increment * a - placeInBucket) * 1.0f / offsetBetweenRounds
            if (doubleRound % 1.0f < 0.0001f) {
                round = doubleRound.roundToInt() % increment
                break
            }
        }

        val initialIndex = (round * deckSize + index) / increment
        return initialIndex
    }
}