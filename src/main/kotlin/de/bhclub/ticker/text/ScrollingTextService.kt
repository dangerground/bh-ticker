package de.bhclub.ticker.text;

import de.bhclub.ticker.playlists.PlaylistNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Optional;

@Service
public class ScrollingTextService {

    @Autowired
    private ScrollingTextRepository scrollingTextRepository;

    public void createText(String text, String textColor, String outline, String background, String font, String speed, String spacing) {
        ScrollingText scrollingText = new ScrollingText();
        scrollingText.setText(text);
        scrollingText.setTextColor(Color.decode(textColor));
        scrollingText.setOutlineColor(Color.decode(outline));
        scrollingText.setBackground(Color.decode(background));
        scrollingText.setFont(font);
        scrollingText.setSpeed(Integer.parseInt(speed));
        scrollingText.setSpacing(Integer.parseInt(spacing));

        scrollingTextRepository.save(scrollingText);
    }

    public Iterable<ScrollingText> getAll() {
        return scrollingTextRepository.findAll();
    }

    public ScrollingText getText(Long id) {
        Optional<ScrollingText> text = scrollingTextRepository.findById(id);
        if (!text.isPresent()) {
            throw new PlaylistNotFoundException("text not found");
        }
        return text.get();
    }

    public static String toHex(Color color) {
        return String.format("#%06X", (0xFFFFFF & color.getRGB()));
    }

    public void deleteText(Long id) {
        scrollingTextRepository.deleteById(id);
    }
}
