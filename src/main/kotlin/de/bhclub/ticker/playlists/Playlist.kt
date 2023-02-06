package de.bhclub.ticker.playlists

import de.bhclub.ticker.gif.Gif
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import java.util.UUID

@Entity
data class Playlist(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
    var shuffle: Boolean = false,
    val waitTime: Int = 0,

    @ManyToMany
    val entries: MutableList<Gif> = mutableListOf(),
)