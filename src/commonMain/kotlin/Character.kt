import com.soywiz.korge.view.Image
import com.soywiz.korge.view.Text
import com.soywiz.korge.view.alpha
import com.soywiz.korge.view.xy
import kotlin.math.abs

class Character (
    var name: String = "",
    var imageFile: String = "pope.png",
    var position: Position = Position(),
) {
    var active : Boolean = false
        set(value) {
            this.image?.alpha(if (value) 1.0 else 0.4)
            field = value
        }

    var image: Image? = null
        get() {
            if (field == null)
                throw Exception("maaaaal")
            return field
        }

    var tooltip: Text? = null
        get() {
            if (field == null)
                throw Exception("maaaaal")
            return field
        }

    constructor(name: String) : this() {
        this.name = name
    }

    fun walk(destiny: Position) : Boolean {
        if(canWalk(destiny)) {
            image?.xy(destiny.x * 51 + 26, destiny.y * 51 + 26)
            tooltip?.xy(destiny.x * 51 + 26, destiny.y * 51 + 26)
            position = destiny
            return true
        }
        return false
    }

    fun canWalk(destiny: Position): Boolean {
        return abs(destiny.x-position.x) <= 3 && abs(destiny.y-position.y) <= 3
    }

    fun canAttack(destiny: Position): Boolean {
        return abs(destiny.x-position.x) <= 2 && abs(destiny.y-position.y) <= 2
    }

    fun toggleActive() {
        active = !active
    }

    override fun toString(): String {
        return name
    }
}