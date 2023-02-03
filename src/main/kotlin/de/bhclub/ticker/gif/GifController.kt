package de.bhclub.ticker.gif

import de.bhclub.ticker.ticker.TickerService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile
import jakarta.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/gif/*")
class GifController(
    private val gifService: GifService,
    private val tickerService: TickerService
) {
    @GetMapping("/manage")
    fun showGifs(model: Model): String {
        model.addAttribute("gifs", gifService.getAllGifs())
        model.addAttribute("selected", gifService.activeGif)
        return "gifs"
    }

    @GetMapping("/upload")
    fun showUpload(): String {
        return "gif_upload"
    }

    @PostMapping("/upload")
    fun doUpload(@RequestParam file: MultipartFile, model: Model): String {
        val gif = gifService.uploadFile(file)
        model.addAttribute("gif", gif)
        return "gif_upload"
    }

    @GetMapping("/show/{fileId:[0-9]+}")
    @ResponseBody
    fun showGifs(@PathVariable fileId: Long): ByteArray? {
        return gifService.getFile(fileId)
    }

    @GetMapping("/play/{fileId:[0-9]+}")
    fun playGif(@PathVariable fileId: Long): String {
        val filePath = gifService.getFileRef(fileId)
        tickerService.playGif(filePath)
        return "redirect:/gif/manage"
    }
}