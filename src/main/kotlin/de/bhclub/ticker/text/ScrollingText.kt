package de.bhclub.ticker.text

import java.awt.Color
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class ScrollingText(
    @Id
    @GeneratedValue
    val id: Long? = null,
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