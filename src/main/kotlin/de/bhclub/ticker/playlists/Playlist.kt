package de.bhclub.ticker.playlists

import de.bhclub.ticker.gif.Gif
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany

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