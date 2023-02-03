package de.bhclub.ticker.ticker;

import de.bhclub.ticker.preferences.PreferencesService;
import de.bhclub.ticker.text.ScrollingText;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TickerService {

    @Autowired
    private PreferencesService preferencesService;

    @Value("${ticker.tools}")
    private String rpiRgbLedMatrixHome;

    private final static Logger logger = Logger.getLogger(TickerService.class.getName());

    private static final String LOOP_FOREVER_PARAMETER = "-f";
    private static final String SHUFFLE_PARAMETER = "-s";

    private static final Collection<String> options = new ArrayList<>();

    private static Process commandProcess;

    private static final String HAS_32_ROWS = "--led-rows=32";
    private static final String HAS_64_COLS = "--led-cols=64";
    private static final String CONSISTS_OF_3_CHAINS = "--led-chain=3";
    private static final String ADAFRUIT_HAT_PWN_GPIO_MAPPING = "--led-gpio-mapping=adafruit-hat-pwm";
    private static final String LED_SLOWDOWN_GPIO_FOR_RPI3 = "--led-slowdown-gpio=2";
    private static final String WAIT_5SEC_BETWEEN_IMAGES = "-w5";

    {
        options.add(HAS_32_ROWS);
        options.add(HAS_64_COLS);
        options.add(CONSISTS_OF_3_CHAINS);
        options.add(ADAFRUIT_HAT_PWN_GPIO_MAPPING);
        options.add(LED_SLOWDOWN_GPIO_FOR_RPI3);
    }

    public void playPlaylist(List<String> entryList, boolean shuffle) throws InterruptedException, IOException {
        killTicker();

        List<String> imageViewer = getImageViewer();

        CommandList command = new CommandList();
        command.addAll(imageViewer);
        command.addAll(options);
        command.add(LOOP_FOREVER_PARAMETER);
        command.add(WAIT_5SEC_BETWEEN_IMAGES);
        command.add("--led-brightness=" + preferencesService.getBrightness());

        if (shuffle) {
            command.add(SHUFFLE_PARAMETER);
        }

        command.addAll(entryList);

        startProgram(command);
    }

    private void startProgram(List<String> command) throws IOException {
        if (command == null || command.isEmpty()) {
            logger.warning("No command given to start");
            return;
        }

        preferencesService.setLastCommand(command);
        String debugInfo = "command list: >" + Strings.join(command, ' ') + "<";
        logger.info(debugInfo);

        ProcessBuilder builder = new ProcessBuilder(command)
                .directory(new File("/home/pi"))
                .inheritIO()
                .redirectErrorStream(true)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT);

        commandProcess = builder.start();
        // do not waitFor() the process to finish, so as to keep the Web UI responsive
    }

    private List<String> getImageViewer() {
        List<String> commands = new ArrayList<>();
        commands.add("sudo");
        commands.add(rpiRgbLedMatrixHome + "/utils/led-image-viewer");
        return commands;
    }

    private List<String> getTextScroller() {
        List<String> commands = new ArrayList<>();
        commands.add("sudo");
        commands.add(rpiRgbLedMatrixHome + "/examples-api-use/scrolling-text-example");
        return commands;
    }

    public void playGif(String file) throws IOException, InterruptedException {
        playPlaylist(Collections.singletonList(file), false);
    }

    public void playText(ScrollingText st) throws IOException, InterruptedException {
        killTicker();

        List<String> imageViewer = getTextScroller();

        CommandList command = new CommandList();
        command.addAll(imageViewer);
        command.addAll(options);
        command.add("-f", "fonts/" + st.getFont() + ".bdf");
        command.add("-y", "-6");
        command.add("-s", st.getSpeed().toString());
        command.add("-S", st.getSpacing().toString());
        command.add("-C", getColorString(st.getTextColor()));
        command.add("-O", getColorString(st.getOutlineColor()));
        command.add("-B", getColorString(st.getBackground()));
        command.add("--led-brightness=" + preferencesService.getBrightness());
        command.add(st.getText());

        startProgram(command);
    }

    private String getColorString(Color textColor) {
        return textColor.getRed() + "," + textColor.getGreen() + "," + textColor.getBlue();
    }

    public void restartTicker() throws IOException, InterruptedException {
        killTicker();
        startProgram(preferencesService.getLastCommand());
    }

    public void killTicker() throws InterruptedException {
        logger.info("trying to stop command " + commandProcess);
        if (commandProcess != null) {
            kill(commandProcess.toHandle());
            logger.info("killed everything, now waiting " + commandProcess);
            commandProcess.waitFor();
            commandProcess = null;
            logger.info("command should have stopped");
        }
    }

    private void kill(ProcessHandle handle) {

        logger.info("kill handle " + handle);
        handle.descendants().forEach(this::kill);
        handle.destroyForcibly();
    }

    public Object isPlaying() {
        return commandProcess != null;
    }

    public static class CommandList extends ArrayList<String> {

        public boolean add(String... list) {
            for (String entry : list) {
                super.add(entry);
            }

            return true;
        }
    }
}
