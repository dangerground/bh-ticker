package de.bhclub.ticker.playlists

import de.bhclub.ticker.gif.Gif
import de.bhclub.ticker.gif.GifService
import de.bhclub.ticker.ticker.TickerService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

@Controller
@RequestMapping("/playlists")
class PlaylistController(
    private val playlistService: PlaylistService,
    private val tickerService: TickerService,
    private val gifService: GifService,
) {
    @GetMapping("overview")
    fun playlists(model: Model): String {
        model.addAttribute("playlists", playlistService.getAll())
        return "playlists"
    }

    @GetMapping("/view/{playlistId}")
    fun view(model: Model, @PathVariable playlistId: UUID): String {
        val pl = playlistService.getPlaylist(playlistId)
        model.addAttribute("playlist", pl)
        val gifs: MutableCollection<Gif?> = ArrayList()
        for (gif in gifService.getAllGifs()) {
            if (!pl.entries.contains(gif)) {
                gifs.add(gif)
            }
        }
        model.addAttribute("gifs", gifs)
        return "playlist"
    }

    @PostMapping("/create")
    fun create(@RequestParam name: String): String {
        val playlist = playlistService.create(name)
        return "redirect:/playlists/view/" + playlist.id
    }

    @PostMapping("/add")
    fun add(
        @RequestParam gifId: UUID,
        @RequestParam playlistId: UUID
    ): String {
        playlistService.addGifToPlaylist(gifId, playlistId)
        return "redirect:/playlists/view/$playlistId"
    }

    @GetMapping("/shufflemode/{playlistId}")
    fun shufflemode(@PathVariable playlistId: UUID): String {
        playlistService.toggleShuffle(playlistId)
        return "redirect:/playlists/view/$playlistId"
    }

    @GetMapping("/play/{playlistId}")
    fun play(@PathVariable playlistId: UUID): String {
        val entity = playlistService.getPlaylist(playlistId)
        tickerService.playPlaylist(playlistService.getImagePaths(entity), entity.shuffle)
        return "redirect:/"
    }

    @GetMapping("/delete/{playlistId}")
    fun delete(@PathVariable("playlistId") playlistId: UUID): String {
        playlistService.delete(playlistId)
        return "redirect:/playlists/overview"
    }
}