package de.bhclub.ticker.playlists

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface PlaylistRepository : CrudRepository<Playlist, UUID>