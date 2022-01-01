import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import kotlin.math.abs

class Game(
    var characters : ArrayList<Character> = arrayListOf(),
    var board: ArrayList<ArrayList<RoundRect>> = arrayListOf(),
    var backgroundImage : Image? = null
)
{
    var activeCharacter : Character? = null

    fun mouseOver(character: Character) {
        if(hasActive()) {
            var square = board[character.position.x][character.position.y]
            if(activeCharacter!!.canAttack(character.position))
                square.fill = Colors.GREEN
            else
                square.fill = Colors.RED
        }
        character.image?.scale(0.1)
        character.tooltip?.visible = true
    }
    fun mouseOut(character: Character) {
        //mouseOut(character.position)
        var square = board[character.position.x][character.position.y]
        square.fill = Colors.BLACK
        square.alpha(0.2)
        character.image?.scale(0.09)
        character.tooltip?.visible = false
    }

    fun click(character: Character) {
        if (hasActive())
            activeCharacter?.active = false
        if (activeCharacter==character)
            activeCharacter = null
        else
            activeCharacter = character
            activeCharacter?.active = true
    }
    fun mouseOver(position: Position) {
        if(empty(position)) {
            var square = board[position.x][position.y]
            if(hasActive()) {
                if(activeCharacter!!.canWalk(position))
                    square.fill = Colors.GREEN
                else
                    square.fill = Colors.RED
            } else {
                square.fill = Colors.AQUAMARINE
            }
            square.alpha(0.4)
        }
    }
    fun mouseOut(position: Position) {
        if (empty(position)) {
            var square = board[position.x][position.y]
            square.fill = Colors.BLACK
            square.alpha(0.2)
        }
    }

    fun click(position: Position) {
        if (empty(position)) {
            if (hasActive()) {
                activeCharacter?.walk(position)
            }
        }
    }

    fun hasActive(): Boolean {
        return activeCharacter!=null
    }

    fun empty(position: Position): Boolean {
        for (character in characters)
            if (character.position.equals(position))
                return false
        return true
    }
}

class Position(var x: Int = 0, var y: Int = 0) {
    override fun toString(): String {
        return "($x, $y)"
    }

    operator fun plusAssign(distance: Position) {
        this.x += distance.x
        this.y += distance.y
    }

    fun equals(other: Position): Boolean {
        return other.x == x && other.y == y
    }
}



