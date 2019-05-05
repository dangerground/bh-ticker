package de.bhclub.ticker.text;

import de.bhclub.ticker.ticker.TickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/text")
public class ScrollingTextController {

    @Autowired
    private ScrollingTextService scrollingTextService;

    @Autowired
    private TickerService tickerService;

    @GetMapping("/list")
    public String showTexts(Model model) {
        model.addAttribute("texts", scrollingTextService.getAll());
        return "texts";
    }

    @PostMapping("/create")
    public String showTexts(@RequestParam("text") String text,
                            @RequestParam("color") String color,
                            @RequestParam("outline") String outline,
                            @RequestParam("background") String background,
                            @RequestParam("font") String font,
                            @RequestParam("speed") String speed,
                            @RequestParam("spacing") String spacing) {
        scrollingTextService.createText(
                text,
                color,
                outline,
                background,
                font,
                speed,
                spacing);
        return "redirect:/text/list";
    }

    @GetMapping("/play/{id}")
    public String playText(@PathVariable("id") Long id) throws IOException, InterruptedException {
        tickerService.playText(scrollingTextService.getText(id));

        return "redirect:/text/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteText(@PathVariable("id") Long id) throws IOException, InterruptedException {
        scrollingTextService.deleteText(id);

        return "redirect:/text/list";
    }
}
