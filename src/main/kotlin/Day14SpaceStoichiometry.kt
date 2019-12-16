import kotlin.math.ceil

class Day14SpaceStoichiometry(reactionStrings: List<String>) {
    private val reactionPattern = Regex("(.*) => ([0-9]* [A-Z]*)")
    private val chemicalPattern = Regex("([0-9]*) ([A-Z]*)")
    private val reactions: Map<String, Reaction>
    private val calculatedCosts: MutableMap<String, Int> = HashMap<String, Int>().toMutableMap()
    private var bestSoFar: Int? = null

    init {
        reactions = HashMap()
        reactionStrings
            .map { parseReaction(it) }
            .forEach { reactions[it.output.second] = it }
    }

    fun oreRequiredForFuel(): Int {
        val fuelReaction = reactions["FUEL"]!!
        val currentRequirements: MutableMap<String, Int> =
            fuelReaction.inputs.map { Pair(it.second, it.first) }.toMap().toMutableMap()
        return lowestCostOfRequirements(currentRequirements)
    }

    private fun lowestCostOfRequirements(currentRequirements: MutableMap<String, Int>): Int {
        val requiredOre = currentRequirements["ORE"]
        if (requiredOre != null && bestSoFar != null && requiredOre >= bestSoFar!!) {
            return bestSoFar!!
        }

        if (requiredOre != null && currentRequirements.size == 1) {
            bestSoFar = requiredOre
            println("bestSoFar = ${bestSoFar}")
            return requiredOre
        }

        val hash = hash(currentRequirements)
        if (!calculatedCosts.containsKey(hash)) {
            calculatedCosts[hash] = currentRequirements.keys
                .filter { it != "ORE" }
                .map { costWithChosenChemical(it, HashMap(currentRequirements).toMutableMap()) }
                .min()!!
        }
        return calculatedCosts[hash]!!
    }

    private fun hash(currentRequirements: MutableMap<String, Int>) =
        currentRequirements.entries.sortedBy { it.key }.map { it.key + "_" + it.value }.joinToString(".")

    private fun costWithChosenChemical(
        chemical: String,
        currentRequirements: MutableMap<String, Int>
    ): Int {
        val requiredQuantity = currentRequirements[chemical]!!
        val reaction = reactions[chemical]!!
        currentRequirements.remove(chemical)
        addInputRequirements(reaction, currentRequirements, resolveReactionMultiplier(reaction, requiredQuantity))
        return lowestCostOfRequirements(currentRequirements)
    }

    private fun resolveReactionMultiplier(
        reaction: Reaction,
        requiredQuantity: Int
    ): Int {
        val producedQuantity = reaction.output.first
        val reactionMultiplier = ceil(requiredQuantity * 1.0 / producedQuantity).toInt()
        return reactionMultiplier
    }

    private fun addInputRequirements(
        reaction: Reaction,
        currentRequirements: MutableMap<String, Int>,
        reactionMultiplier: Int
    ) {
        for (input in reaction.inputs) {
            val inputChemical = input.second
            val inputQuantity = input.first
            currentRequirements[inputChemical] =
                (currentRequirements[inputChemical] ?: 0) + inputQuantity * reactionMultiplier
        }
    }

    fun parseReaction(reactionString: String): Reaction {
        val matchedGroups = reactionPattern.matchEntire(reactionString)!!.groupValues
        val output = chemicalPattern.matchEntire(matchedGroups.last())!!.groupValues
        val outputChemical = Pair(output[1].toInt(), output[2])
        val inputs = matchedGroups[1].split(", ")
        val inputChemicals = inputs
            .filter { it != "" }
            .map { chemicalPattern.matchEntire(it)!!.groupValues }
            .map { Pair(it[1].toInt(), it[2]) }
            .toList()
        return Reaction(inputChemicals, outputChemical)
    }

    data class Reaction(val inputs: List<Pair<Int, String>>, val output: Pair<Int, String>)
}