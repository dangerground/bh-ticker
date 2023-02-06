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
import org.springframework.http.MediaType
import java.util.UUID

@Controller
@RequestMapping("/gif")
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

    @GetMapping("/show/{fileId}")
    @ResponseBody
    fun showGifs(response: HttpServletResponse, @PathVariable fileId: UUID): ByteArray? {
        val gif = gifService.getFile(fileId) ?: return null

        return gif.data
    }

    @GetMapping("/play/{fileId}")
    fun playGif(@PathVariable fileId: UUID): String {
        val filePath = gifService.getFileRef(fileId)
        tickerService.playGif(filePath)
        return "redirect:/gif/manage"
    }
}