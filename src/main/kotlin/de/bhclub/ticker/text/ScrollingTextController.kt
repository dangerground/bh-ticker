package de.bhclub.ticker.text

import de.bhclub.ticker.ticker.TickerService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/text")
class ScrollingTextController(
    private val scrollingTextService: ScrollingTextService,
    private val tickerService: TickerService,
) {
    @GetMapping("/list")
    fun showTexts(model: Model): String {
        model.addAttribute("texts", scrollingTextService.getAll())
        return "texts"
    }

    @PostMapping("/create")
    fun showTexts(
        @RequestParam("text") text: String,
        @RequestParam("color") color: String,
        @RequestParam("outline") outline: String,
        @RequestParam("background") background: String,
        @RequestParam("font") font: String,
        @RequestParam("speed") speed: String,
        @RequestParam("spacing") spacing: String
    ): String {
        scrollingTextService.createText(
            text,
            color,
            outline,
            background,
            font,
            speed,
            spacing
        )
        return "redirect:/text/list"
    }

    @GetMapping("/play/{id}")
    fun playText(@PathVariable("id") id: Long): String {
        tickerService.playText(scrollingTextService.getText(id))
        return "redirect:/text/list"
    }

    @GetMapping("/delete/{id}")
    fun deleteText(@PathVariable("id") id: Long): String {
        scrollingTextService.deleteText(id)
        return "redirect:/text/list"
    }
}