package de.bhclub.ticker.preferences;

import de.bhclub.ticker.ticker.TickerService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PreferencesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreferencesService.class);

    public static final String LAST_COMMAND = "last_command";

    @Autowired
    private PreferencesRepository preferencesRepository;

    public void setBrightness(int brightness) {
        PreferencesEntity entity = new PreferencesEntity();
        entity.setId("brightness");
        entity.setValue(String.valueOf(brightness));
        preferencesRepository.save(entity);
    }

    public int getBrightness() {
        Optional<PreferencesEntity> entity = preferencesRepository.findById("brightness");

        return entity.map(e -> Integer.valueOf(e.getValue())).orElse(100);
    }

    public void setLastCommand(List<String> commandList) {
        if (commandList == null) {
            preferencesRepository.deleteById(LAST_COMMAND);
        }
        PreferencesEntity entity = new PreferencesEntity();
        entity.setId(LAST_COMMAND);
        String value = Strings.join(commandList, ' ');
        entity.setValue(value);
        preferencesRepository.save(entity);
        LOGGER.info("Save last program {}", value);
    }

    public TickerService.CommandList getLastCommand() {
        TickerService.CommandList commands = new TickerService.CommandList();
        Optional<PreferencesEntity> entity = preferencesRepository.findById(LAST_COMMAND);

        if (entity.isPresent()) {
            String commandList = entity.get().getValue();
            LOGGER.info("Found last program: {}", commandList);
            if (!Strings.isBlank(commandList)) {
                commands.addAll(Arrays.asList(commandList.split(" ")));
            }
            LOGGER.info("commandList loaded: {}", commands);
        }

        return commands;
    }


}
