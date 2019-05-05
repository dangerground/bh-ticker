package de.bhclub.ticker.playlists;

import de.bhclub.ticker.gif.Gif;
import de.bhclub.ticker.gif.GifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private GifService gifService;

    public Iterable<Playlist> getPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist getPlaylist(long playlistId) {
        Optional<Playlist> entity = playlistRepository.findById(playlistId);
        if (entity.isPresent()) {
            return entity.get();
        }

        throw new InvalidParameterException("playlist not found");
    }

    public List<String> getImagePaths(Playlist entity) {
        return entity.getEntries().stream()
                .map(Gif::getId)
                .map(id -> gifService.getFileRef(id))
                .collect(Collectors.toList());
    }


    public void delete(long playlistId) {
        playlistRepository.deleteById(playlistId);
    }

    public Playlist create(String name) {
        Playlist playlist = new Playlist();
        playlist.setName(name);

        return playlistRepository.save(playlist);
    }

    public void toggleShuffle(long playlistId) {
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        playlist.ifPresentOrElse(
                pl -> {
                    pl.setShuffle(!pl.isShuffle());
                    playlistRepository.save(pl);
                },
                () -> { throw new PlaylistNotFoundException("playlist not found"); }
        );

    }

    public void addGifToPlaylist(long gifId, long playlistId) {
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        playlist.ifPresentOrElse(pl -> {
                    pl.getEntries().add(gifService.getGif(gifId));
                    playlistRepository.save(pl);
                },
                () -> { throw new PlaylistNotFoundException("playlist not found"); }
        );


    }

    public Iterable<Playlist> getAll() {
        return playlistRepository.findAll();
    }
}
