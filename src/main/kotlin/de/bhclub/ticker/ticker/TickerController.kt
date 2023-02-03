package de.bhclub.ticker.ticker

import de.bhclub.ticker.preferences.PreferencesService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TickerController(
    private val tickerService: TickerService,
    private val preferencesService: PreferencesService,
) {
    @GetMapping("/")
    fun showTickerStatus(model: Model): String {
        model.addAttribute("isPlaying", tickerService.isPlaying)
        return "status"
    }

    @GetMapping("/kill")
    fun killTicker(model: Model): String {
        tickerService.killTicker()
        preferencesService.setLastCommand(null)
        model.addAttribute("isPlaying", tickerService.isPlaying)
        return "status"
    }
}