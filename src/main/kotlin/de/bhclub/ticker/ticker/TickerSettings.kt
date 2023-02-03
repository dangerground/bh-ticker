package de.bhclub.ticker.ticker

import org.springframework.boot.context.properties.ConfigurationProperties
import kotlin.properties.Delegates

@ConfigurationProperties(prefix = "ticker.settings")
class TickerSettings {
    var brightness by Delegates.notNull<Int>()
}