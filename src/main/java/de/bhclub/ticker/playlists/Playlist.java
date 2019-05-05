package de.bhclub.ticker.playlists;


import de.bhclub.ticker.gif.Gif;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Playlist {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private boolean shuffle;

    private double waitTime;

    @ManyToMany
    private List<Gif> entries = new ArrayList<>();
}
