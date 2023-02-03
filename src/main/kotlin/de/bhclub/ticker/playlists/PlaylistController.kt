package de.bhclub.ticker.playlists

import de.bhclub.ticker.gif.Gif
import de.bhclub.ticker.gif.GifService
import de.bhclub.ticker.ticker.TickerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.io.IOException

@Controller
@RequestMapping("/playlists")
class PlaylistController(
    private val playlistService: PlaylistService,
    private val tickerService: TickerService,
    private val gifService: GifService,
) {
    @GetMapping("overview")
    fun playlists(model: Model): String {
        model.addAttribute("playlists", playlistService.all)
        return "playlists"
    }

    @GetMapping("/view/{playlistId:[0-9]+}")
    fun view(model: Model, @PathVariable("playlistId") playlistId: Long): String {
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
        @RequestParam gifId: Long,
        @RequestParam playlistId: Long
    ): String {
        playlistService.addGifToPlaylist(gifId, playlistId)
        return "redirect:/playlists/view/$playlistId"
    }

    @GetMapping("/shufflemode/{playlistId:[0-9]+}")
    fun shufflemode(@PathVariable playlistId: Long): String {
        playlistService.toggleShuffle(playlistId)
        return "redirect:/playlists/view/$playlistId"
    }

    @GetMapping("/play/{playlistId:[0-9]+}")
    fun play(@PathVariable("playlistId") playlistId: Long): String {
        val entity = playlistService.getPlaylist(playlistId)
        tickerService.playPlaylist(playlistService.getImagePaths(entity), entity.shuffle)
        return "redirect:/"
    }

    @GetMapping("/delete/{playlistId:[0-9]+}")
    fun delete(@PathVariable("playlistId") playlistId: Long): String {
        playlistService.delete(playlistId)
        return "redirect:/playlists/overview"
    }
}