import java.lang.Math.abs

class Day3CrossedWires {
    fun manhattanDistanceToClosestIntersection(wire1: List<String>, wire2: List<String>): Int {
        val wire1Points = toPositions(wire1)
        val wire2Points = toPositions(wire2)
        wire1Points.retainAll(wire2Points)
        return wire1Points.stream()
            .mapToInt { abs(it.x) + abs(it.y) }
            .min()
            .orElse(0)
    }

    private fun toPositions(wirePath: List<String>): HashSet<Position> {
        val wire1Points = HashSet<Position>()
        var current = Position(x = 0, y = 0)
        for (turn in wirePath) {
            val direction = turn[0]
            val steps = turn.substring(1).toInt()
            when (direction) {
                'U' -> {
                    for (y in current.y + 1 until current.y + steps) {
                        wire1Points.add(Position(x = current.x, y = y))
                    }
                    current = Position(x = current.x, y = current.y + steps)
                }
                'D' -> {
                    for (y in current.y - steps until current.y - 1) {
                        wire1Points.add(Position(x = current.x, y = y))
                    }
                    current = Position(x = current.x, y = current.y - steps)
                }
                'R' -> {
                    for (x in current.x + 1 until current.x + steps) {
                        wire1Points.add(Position(x = x, y = current.y))
                    }
                    current = Position(x = current.x + steps, y = current.y)
                }
                'L' -> {
                    for (x in current.x - steps until current.x - 1) {
                        wire1Points.add(Position(x = x, y = current.y))
                    }
                    current = Position(x = current.x - steps, y = current.y)
                }
            }
        }
        return wire1Points
    }

    private data class Position(val x: Int, val y: Int)
}