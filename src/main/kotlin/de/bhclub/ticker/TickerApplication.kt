package de.bhclub.ticker

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class TickerApplication

fun main(args: Array<String>) {
    SpringApplication.run(TickerApplication::class.java, *args)
}