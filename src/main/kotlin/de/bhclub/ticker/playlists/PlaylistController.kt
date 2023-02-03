package de.bhclub.ticker.playlists;

import de.bhclub.ticker.gif.Gif;
import de.bhclub.ticker.gif.GifService;
import de.bhclub.ticker.ticker.TickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private TickerService tickerService;

    @Autowired
    private GifService gifService;

    @GetMapping("overview")
    public String playlists(Model model) {
        model.addAttribute("playlists", playlistService.getAll());
        return "playlists";
    }

    @GetMapping("/view/{playlistId:[0-9]+}")
    public String view(Model model, @PathVariable("playlistId") long playlistId) {
        Playlist pl = playlistService.getPlaylist(playlistId);
        model.addAttribute("playlist", pl);

        Collection<Gif> gifs = new ArrayList<>();
        for (Gif gif : gifService.getAllGifs()) {
            if (!pl.getEntries().contains(gif)) {
                gifs.add(gif);
            }
        }

        model.addAttribute("gifs", gifs);
        return "playlist";
    }

    @PostMapping("/create")
    public String create(@RequestParam("name") String name) {
        Playlist playlist = playlistService.create(name);
        return "redirect:/playlists/view/"+playlist.getId();
    }

    @PostMapping("/add")
    public String add(@RequestParam("gifId") long gifId,
                      @RequestParam("playlistId") long playlistId) {
        playlistService.addGifToPlaylist(gifId, playlistId);
        return "redirect:/playlists/view/"+playlistId;
    }

    @GetMapping("/shufflemode/{playlistId:[0-9]+}")
    public String shufflemode(@PathVariable("playlistId") long playlistId) {
        playlistService.toggleShuffle(playlistId);
        return "redirect:/playlists/view/"+playlistId;
    }

    @GetMapping("/play/{playlistId:[0-9]+}")
    public String play(@PathVariable("playlistId") long playlistId) throws IOException, InterruptedException {
        Playlist entity = playlistService.getPlaylist(playlistId);
        tickerService.playPlaylist(playlistService.getImagePaths(entity), entity.isShuffle());
        return "redirect:/";
    }

    @GetMapping("/delete/{playlistId:[0-9]+}")
    public String delete(@PathVariable("playlistId") long playlistId) {
        playlistService.delete(playlistId);
        return "redirect:/playlists/overview";
    }
}
