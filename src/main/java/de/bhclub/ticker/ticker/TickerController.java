package de.bhclub.ticker.ticker;

import de.bhclub.ticker.preferences.PreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TickerController {

    @Autowired
    private TickerService tickerService;

    @Autowired
    private PreferencesService preferencesService;

    @GetMapping("/")
    public String showTickerStatus(Model model) {
        model.addAttribute("isPlaying", tickerService.isPlaying());
        return "status";
    }

    @GetMapping("/kill")
    public String killTicker(Model model) throws InterruptedException {
        tickerService.killTicker();
        preferencesService.setLastCommand(null);
        model.addAttribute("isPlaying", tickerService.isPlaying());
        return "status";
    }
}
