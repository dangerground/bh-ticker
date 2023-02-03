package de.bhclub.ticker.playlists

import de.bhclub.ticker.gif.Gif
import de.bhclub.ticker.gif.GifService
import org.springframework.stereotype.Service
import java.security.InvalidParameterException

@Service
class PlaylistService(
    private val playlistRepository: PlaylistRepository,
    private val gifService: GifService,
) {
    fun getPlayList() = playlistRepository.findAll()

    fun getPlaylist(playlistId: Long): Playlist {
        val entity = playlistRepository.findById(playlistId)
        if (entity.isPresent) {
            return entity.get()
        }
        throw InvalidParameterException("playlist not found")
    }

    fun getImagePaths(entity: Playlist): List<String> {
        return entity.entries
            .map(Gif::id)
            .mapNotNull { gifService.getFileRef(it) }
            .toList()
    }

    fun delete(playlistId: Long) {
        playlistRepository.deleteById(playlistId)
    }

    fun create(name: String): Playlist {
        val playlist = Playlist(
            name = name,
        )
        return playlistRepository.save(playlist)
    }

    fun toggleShuffle(playlistId: Long) {
        val playlist = playlistRepository.findById(playlistId).orElse(null)
            ?: throw PlaylistNotFoundException("playlist not found")

        playlist.shuffle = !playlist.shuffle
        playlistRepository.save(playlist)
    }

    fun addGifToPlaylist(gifId: Long, playlistId: Long) {
        val playlist = playlistRepository.findById(playlistId).orElse(null)
            ?: throw PlaylistNotFoundException("playlist not found")

        gifService.getGif(gifId)?.let {
            playlist.entries.add(it)
            playlistRepository.save(playlist)
        }
    }

    val all: Iterable<Playlist?>
        get() = playlistRepository.findAll()
}