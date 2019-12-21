import Util.Companion.toQueue
import java.util.*
import kotlin.collections.HashMap

class Day11SpacePolice {

    fun numberOfPanelsPainted(program: LongArray): Int {
        return paintedPanels(program, 0).size
    }

    fun paintedPanels(program: LongArray, input: Int): Map<Position, Int> {
        val robot = IntComputer(program)
        val paintedPanels = HashMap<Position, Int>().toMutableMap()
        var robotPosition = Position(0, 0)
        var robotDirection = Direction.UP

        var paintOutput = robot.nextOutput(toQueue(input.toLong()))
        var paintInstruction = paintOutput.output!!.toInt()
        paintedPanels[robotPosition] = paintInstruction

        var directionOutput = robot.nextOutput(ArrayDeque<Long>())
        var directionInstruction = directionOutput.output!!.toInt()
        robotDirection = newDirection(robotDirection, directionInstruction)
        robotPosition = newPosition(robotPosition, robotDirection)
        while (true) {
            paintOutput = robot.nextOutput(toQueue((paintedPanels[robotPosition] ?: 0).toLong()))
            if (paintOutput.done) {
                break
            }
            paintInstruction = paintOutput.output!!.toInt()
            paintedPanels[robotPosition] = paintInstruction

            directionOutput = robot.nextOutput(ArrayDeque<Long>())
            if (directionOutput.done) {
                break
            }
            directionInstruction = directionOutput.output!!.toInt()
            robotDirection = newDirection(robotDirection, directionInstruction)
            robotPosition = newPosition(robotPosition, robotDirection)
        }
        return paintedPanels
    }

    private fun newPosition(
        position: Position,
        direction: Direction
    ): Position {
        return when (direction) {
            Direction.UP -> Position(position.x, position.y - 1)
            Direction.DOWN -> Position(position.x, position.y + 1)
            Direction.RIGHT -> Position(position.x + 1, position.y)
            Direction.LEFT -> Position(position.x - 1, position.y)
        }
    }

    private fun newDirection(
        currentDirection: Direction,
        instruction: Int
    ): Direction {
        val turnRight = instruction == 1
        val turnLeft = instruction == 0
        return when {
            currentDirection == Direction.UP && turnRight -> Direction.RIGHT
            currentDirection == Direction.UP && turnLeft -> Direction.LEFT
            currentDirection == Direction.DOWN && turnRight -> Direction.LEFT
            currentDirection == Direction.DOWN && turnLeft -> Direction.RIGHT
            currentDirection == Direction.RIGHT && turnRight -> Direction.DOWN
            currentDirection == Direction.RIGHT && turnLeft -> Direction.UP
            currentDirection == Direction.LEFT && turnRight -> Direction.UP
            currentDirection == Direction.LEFT && turnLeft -> Direction.DOWN

            else -> throw IllegalStateException()
        }
    }

    data class Position(val x: Int, val y: Int)

    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }
}