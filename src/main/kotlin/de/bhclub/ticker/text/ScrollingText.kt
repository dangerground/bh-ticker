package de.bhclub.ticker.text

import java.awt.Color
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Entity
data class ScrollingText(
    @Id
    val id: UUID = UUID.randomUUID(),
    val text: String,
    val textColor: Color,
    val outlineColor: Color,
    val background: Color,
    val font: String,
    val spacing: Int,
    val speed: Int,
    val startX: Int? = null,
    val startY: Int? = null,
)