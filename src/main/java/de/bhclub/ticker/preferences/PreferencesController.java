package de.bhclub.ticker.preferences;

import de.bhclub.ticker.ticker.TickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class PreferencesController {

    @Autowired
    private PreferencesService preferencesService;

    @Autowired
    private TickerService tickerService;

    @GetMapping("/preferences")
    public String preferences(Model model) {
        model.addAttribute("brightness", preferencesService.getBrightness());
        return "preferences";
    }

    @PostMapping("/preferences/save")
    public String savePreferences(@RequestParam("brightness") int brightness) throws IOException, InterruptedException {
        preferencesService.setBrightness(brightness);
        tickerService.restartTicker();
        return "redirect:/preferences";
    }
}
