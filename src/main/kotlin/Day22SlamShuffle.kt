import java.math.BigInteger

class Day22SlamShuffle {
    fun cardIndexAfterShuffles(startIndex: Long, deckSize: Long, shuffles: List<String>): Long {
        var index = startIndex
        for (shuffle in shuffles) {
            val newIndex = indexAfterShuffle(index, deckSize, shuffle)
            index = newIndex
        }
        return index
    }

    private fun indexAfterShuffle(index: Long, deckSize: Long, shuffle: String): Long {
        when {
            shuffle == "deal into new stack" -> return deckSize - index - 1
            shuffle.startsWith("cut ") -> return cut(
                index,
                deckSize,
                shuffle.substring(4).toInt()
            )
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

    private fun dealWithIncrement(index: Long, deckSize: Long, increment: Int): Long {
        return (index * increment) % deckSize
    }

    fun cardIndexBeforeShuffles(
        indexAtEnd: Long,
        deckSize: Long,
        shuffles: List<String>
    ): BigInteger {
        var index = indexAtEnd
        val reversedShuffles = shuffles.reversed()
        for (shuffle in reversedShuffles) {
            val newIndex = indexBeforeShuffle(index, deckSize, shuffle)
            index = newIndex
        }
        return positiveMod(BigInteger.valueOf(index), BigInteger.valueOf(deckSize))
    }

    private fun indexBeforeShuffle(index: Long, deckSize: Long, shuffle: String): Long {
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
        val incrementBigInt = BigInteger.valueOf(increment.toLong())
        val decksizeBigInt = BigInteger.valueOf(deckSize)
        val initialIndex =
            positiveMod(BigInteger.valueOf(index) * incrementBigInt.modInverse(decksizeBigInt), decksizeBigInt)
        return initialIndex.toLong()
    }

    private fun positiveMod(value: BigInteger, modValue: BigInteger): BigInteger {
        return ((value % modValue) + modValue) % modValue
    }

    fun giveUpUseRedditMath(
        indexAtEnd: BigInteger,
        deckSize: BigInteger,
        numberOfTimesToRepeat: BigInteger,
        shuffles: List<String>
    ): BigInteger {
        val b = BigInteger.valueOf(Day22SlamShuffle().cardIndexAfterShuffles(0, deckSize.toLong(), shuffles))
        val a = BigInteger.valueOf(Day22SlamShuffle().cardIndexAfterShuffles(1, deckSize.toLong(), shuffles)) - b

        val a_n = a.modPow(numberOfTimesToRepeat, deckSize)
        val b_n = b * (a_n - BigInteger.ONE) * (a - BigInteger.ONE).modInverse(deckSize)

        val rawStartIndex = (indexAtEnd - b_n) * a_n.modInverse(deckSize)
        return positiveMod(rawStartIndex, deckSize)
    }
}
