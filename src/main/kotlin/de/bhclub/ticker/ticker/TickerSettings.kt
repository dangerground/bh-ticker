package de.bhclub.ticker.ticker;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ticker.settings")
public class TickerSettings {
    private int brightness;

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }
}
