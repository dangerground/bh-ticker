package de.bhclub.ticker.text

import de.bhclub.ticker.playlists.PlaylistNotFoundException
import org.springframework.stereotype.Service
import java.awt.Color
import java.util.UUID

@Service
class ScrollingTextService(
    private val scrollingTextRepository: ScrollingTextRepository,
) {
    fun createText(
        text: String,
        textColor: String,
        outline: String,
        background: String,
        font: String,
        speed: String,
        spacing: String
    ) {
        val scrollingText = ScrollingText(
            text = text,
            textColor = Color.decode(textColor),
            outlineColor = Color.decode(outline),
            background = Color.decode(background),
            font = font,
            speed = speed.toInt(),
            spacing = spacing.toInt(),
        )
        scrollingTextRepository.save(scrollingText)
    }

    fun getAll() = scrollingTextRepository.findAll()

    fun getText(id: UUID): ScrollingText {
        val text = scrollingTextRepository.findById(id)
        if (!text.isPresent) {
            throw PlaylistNotFoundException("text not found")
        }
        return text.get()
    }

    fun deleteText(id: UUID) {
        scrollingTextRepository.deleteById(id)
    }

    companion object {
        @JvmStatic
        fun toHex(color: Color): String {
            return String.format("#%06X", 0xFFFFFF and color.rgb)
        }
    }
}