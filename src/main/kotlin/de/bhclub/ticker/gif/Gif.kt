package de.bhclub.ticker.gif

import de.bhclub.ticker.playlists.Playlist
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import java.util.UUID

@Entity
data class Gif(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String?,
    val path: String,

    @Column(columnDefinition = "BLOB")
    val data: ByteArray,

    @ManyToMany(mappedBy = "entries")
    val playlists: List<Playlist> = listOf()
)