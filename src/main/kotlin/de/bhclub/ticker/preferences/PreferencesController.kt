package de.bhclub.ticker.preferences

import de.bhclub.ticker.ticker.TickerService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class PreferencesController(
    private val preferencesService: PreferencesService,
    private val tickerService: TickerService,
) {


    @GetMapping("/preferences")
    fun preferences(model: Model): String {
        model.addAttribute("brightness", preferencesService.getBrightness())
        return "preferences"
    }

    @PostMapping("/preferences/save")
    fun savePreferences(@RequestParam brightness: Int): String {
        preferencesService.setBrightness(brightness)
        tickerService.restartTicker()
        return "redirect:/preferences"
    }
}