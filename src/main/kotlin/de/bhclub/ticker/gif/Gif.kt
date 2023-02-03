package de.bhclub.ticker.gif

import de.bhclub.ticker.playlists.Playlist
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
data class Gif(
    @Id
    @GeneratedValue
    val id: Long = 0,
    val name: String?,
    val path: String,

    @Column(columnDefinition = "BLOB")
    val data: ByteArray,

    @ManyToMany(mappedBy = "entries")
    val playlists: List<Playlist> = listOf()
)