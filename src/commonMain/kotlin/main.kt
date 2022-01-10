import com.soywiz.korge.Korge
import com.soywiz.korge.input.mouse
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

suspend fun main() = Korge(width = 622, height = 512, bgcolor = Colors["#2b2b2b"]) {
	// Crea juego y personajes
	var game = Game(
			arrayListOf(
				Character("Maltús"),
				Character("Maria", "arch.png", Position(6,0)),
				Character("David", "arch.png", Position(2,0)),
				Character("Ale", "barb.png", Position(6,6)),
				Character("JA", "warr.png", Position(8,5)),
				Character("Juan", "wiza.png", Position(3,3)),
			)
	)

	// Carga el tablero
	game.backgroundImage = image(resourcesVfs["background.png"].readBitmap())
	for (i in 0..9) {
		val row : ArrayList<RoundRect> = arrayListOf()
		for (j in 0..9) {
			row.add(roundRect(width = 50.0, height = 50.0, rx = 3.0, fill = Colors.BLACK) {
				xy(i * 51 + 1, j * 51 + 1)
				alpha(0.2)
				mouse {
					over {
						game.mouseOver(Position(i, j))
					}
					out  {
						game.mouseOut(Position(i, j))
					}
					click {
						game.click(Position(i, j))
					}
				}
			})
		}
		game.board.add(row)
	}

	// Carga las imágenes en los objetos de personajes
	for (character in game.characters) {
		val displayPosition = Position(character.position.x * 51 + 26, character.position.y * 51 + 26)
		val image = image(resourcesVfs[character.imageFile].readBitmap()) {
			anchor(.5, .5)
			scale(.09)
			position(displayPosition.x, displayPosition.y)
			alpha(0.4)
		}
		image.mouse {
			over {
				game.mouseOver(character)
			}
			out {
				game.mouseOut(character)
			}
			click {
				game.click(character)
			}
		}
		character.image = image
		character.tooltip = text(character.name, textSize = 16.0, color = Colors.LIGHTSALMON)
								.xy(displayPosition.x, displayPosition.y)
								.visible(false)
	}

	// Game loop
/*	while (true) {
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
	}*/
}