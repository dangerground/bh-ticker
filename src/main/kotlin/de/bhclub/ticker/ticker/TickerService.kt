package de.bhclub.ticker.ticker

import de.bhclub.ticker.preferences.PreferencesService
import de.bhclub.ticker.text.ScrollingText
import mu.KLogging
import org.apache.logging.log4j.util.Strings
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.awt.Color
import java.io.File

@Service
class TickerService(
    private val preferencesService: PreferencesService,
    @Value("\${ticker.tools}")
    private val rpiRgbLedMatrixHome: String,
) : KLogging() {
    init {
        options.add(HAS_32_ROWS)
        options.add(HAS_64_COLS)
        options.add(CONSISTS_OF_3_CHAINS)
        options.add(ADAFRUIT_HAT_PWN_GPIO_MAPPING)
        options.add(LED_SLOWDOWN_GPIO_FOR_RPI3)
    }

    fun playPlaylist(entryList: List<String>, shuffle: Boolean) {
        killTicker()
        val imageViewer = getImageViewer()
        val command = CommandList()
        command.addAll(imageViewer)
        command.addAll(options)
        command.add(LOOP_FOREVER_PARAMETER)
        command.add(WAIT_5SEC_BETWEEN_IMAGES)
        command.add("--led-brightness=" + preferencesService.getBrightness())
        if (shuffle) {
            command.add(SHUFFLE_PARAMETER)
        }
        command.addAll(entryList)
        startProgram(command)
    }

    private fun startProgram(command: List<String>?) {
        if (command.isNullOrEmpty()) {
            logger.warn { "No command given to start" }
            return
        }
        preferencesService.setLastCommand(command)
        val debugInfo = "command list: >" + Strings.join(command, ' ') + "<"
        logger.info { debugInfo }
        val builder = ProcessBuilder(command)
            .directory(File("/home/pi"))
            .inheritIO()
            .redirectErrorStream(true)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        commandProcess = builder.start()
        // do not waitFor() the process to finish, so as to keep the Web UI responsive
    }

    private fun getImageViewer(): List<String> {
        val commands: MutableList<String> = ArrayList()
        commands.add("sudo")
        commands.add("$rpiRgbLedMatrixHome/utils/led-image-viewer")
        return commands
    }

    private fun getTextScroller(): List<String> {
        val commands: MutableList<String> = ArrayList()
        commands.add("sudo")
        commands.add("$rpiRgbLedMatrixHome/examples-api-use/scrolling-text-example")
        return commands
    }

    fun playGif(file: String?) {
        file?.let {
            playPlaylist(listOf(it), false)
        }
    }

    fun playText(st: ScrollingText) {
        killTicker()
        val imageViewer = getTextScroller()
        val command = CommandList()
        command.addAll(imageViewer)
        command.addAll(options)
        command.add("-f", "fonts/" + st.font + ".bdf")
        command.add("-y", "-6")
        command.add("-s", st.speed.toString())
        command.add("-S", st.spacing.toString())
        command.add("-C", getColorString(st.textColor))
        command.add("-O", getColorString(st.outlineColor))
        command.add("-B", getColorString(st.background))
        command.add("--led-brightness=" + preferencesService.getBrightness())
        command.add(st.text)
        startProgram(command)
    }

    private fun getColorString(textColor: Color): String {
        return textColor.red.toString() + "," + textColor.green + "," + textColor.blue
    }

    fun restartTicker() {
        killTicker()
        startProgram(preferencesService.getLastCommand())
    }

    fun killTicker() {
        logger.info { "trying to stop command $commandProcess" }
        if (commandProcess != null) {
            commandProcess?.let { kill(it.toHandle()) }
            logger.info { "killed everything, now waiting $commandProcess" }
            commandProcess?.waitFor()
            commandProcess = null
            logger.info { "command should have stopped" }
        }
    }

    private fun kill(handle: ProcessHandle) {
        logger.info { "kill handle $handle" }
        handle.descendants().forEach { kill(it) }
        handle.destroyForcibly()
    }

    val isPlaying: Any
        get() = commandProcess != null

    class CommandList : ArrayList<String>() {
        fun add(vararg list: String): Boolean {
            for (entry in list) {
                super.add(entry)
            }
            return true
        }
    }

    companion object {
        private const val LOOP_FOREVER_PARAMETER = "-f"
        private const val SHUFFLE_PARAMETER = "-s"
        private val options: MutableCollection<String> = mutableListOf()
        private var commandProcess: Process? = null
        private const val HAS_32_ROWS = "--led-rows=32"
        private const val HAS_64_COLS = "--led-cols=64"
        private const val CONSISTS_OF_3_CHAINS = "--led-chain=3"
        private const val ADAFRUIT_HAT_PWN_GPIO_MAPPING = "--led-gpio-mapping=adafruit-hat-pwm"
        private const val LED_SLOWDOWN_GPIO_FOR_RPI3 = "--led-slowdown-gpio=2"
        private const val WAIT_5SEC_BETWEEN_IMAGES = "-w5"
    }
}