import com.soywiz.klock.seconds
import com.soywiz.korge.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.NativeImage
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.interpolation.Easing

class Position(var x: Int = 0, var y: Int = 0) {
	override fun toString(): String {
		return "($x, $y)"
	}

	operator fun plusAssign(distance: Position) {
		this.x += distance.x
		this.y += distance.y
	}
}

class Character(
	var name: String = "",
	var imageFile: String = "pope.png",
	var position: Position = Position()
) {
	var image: Image? = null
		get() {
			if (field == null)
				throw Exception("maaaaal")
			return field
		}

	constructor(name: String) : this() {
		this.name = name
	}

	public fun walk(distance: Position) : Boolean {
		this.position += distance
		return true
	}

	fun loadImage(image: Image) {
		this.image = image
	}
	override fun toString(): String {
		return "$name - $position"
	}
}

suspend fun main() = Korge(width = 512, height = 512, bgcolor = Colors["#2b2b2b"]) {
	val minScale = 0.09
	val maxScale = 0.11

	var characters : ArrayList<Character> = arrayListOf(
		Character("Bartús"),
		Character("David", "arch.png", Position(2,0)),
		Character("Ale", "barb.png", Position(6,6)),
		Character("JA", "warr.png", Position(8,5)),
		Character("Juan", "wiza.png", Position(3,3)),
	)

	// Carga las imágenes en los objetos de personajes
	for (character in characters) {
		val image = image(resourcesVfs[character.imageFile].readBitmap()) {
			anchor(.5, .5)
			scale(.1)
			position(character.position.x * 51 + 30, character.position.y * 51 + 30)
		}
		character.loadImage(image)
	}

	// Game loop
	while (true) {
		for (character in characters) {

			character.image.tween(
				character.image!!::scale[minScale],
				time = 0.1.seconds,
				easing = Easing.EASE_IN_OUT
			)
			character.image.tween(
				character.image!!::scale[maxScale],
				time = 0.1.seconds,
				easing = Easing.EASE_IN_OUT
			)
		}
		// Read input (mouse or keyboard)
		// Wait for fps
	}
}