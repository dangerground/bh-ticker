package de.bhclub.ticker.preferences

import de.bhclub.ticker.ticker.TickerService.CommandList
import org.apache.logging.log4j.util.Strings
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PreferencesService(
    private val preferencesRepository: PreferencesRepository,
) {
    fun getBrightness(): Int {
        val entity = preferencesRepository.findById("brightness")
        return entity.map { it.value.toInt() }
            .orElse(100)
    }

    fun setBrightness(brightness: Int) {
        val entity = PreferencesEntity(
            key = "brightness",
            value = brightness.toString()
        )
        preferencesRepository.save(entity)
    }

    fun getLastCommand(): CommandList {
        val commands = CommandList()
        val entity = preferencesRepository.findById(LAST_COMMAND)
        if (entity.isPresent) {
            val commandList: String = entity.get().value
            LOGGER.info("Found last program: {}", commandList)
            if (!Strings.isBlank(commandList)) {
                commands.addAll(listOf(*commandList.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()))
            }
            LOGGER.info("commandList loaded: {}", commands)
        }
        return commands
    }

    fun setLastCommand(commandList: List<String>?) {
        if (commandList == null) {
            preferencesRepository.deleteById(LAST_COMMAND)
        }
        val value = Strings.join(commandList, ' ')
        val entity = PreferencesEntity(
            key = LAST_COMMAND,
            value = value
        )
        preferencesRepository.save(entity)
        LOGGER.info("Save last program {}", value)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(PreferencesService::class.java)
        const val LAST_COMMAND = "last_command"
    }
}