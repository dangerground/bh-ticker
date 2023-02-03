package de.bhclub.ticker.playlists

import de.bhclub.ticker.gif.Gif
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany

@Entity
data class Playlist(
    @Id
    @GeneratedValue
    val id: Long = 0,
    val name: String,
    var shuffle: Boolean = false,
    val waitTime: Int = 0,

    @ManyToMany
    val entries: MutableList<Gif> = mutableListOf(),
)