package com.steca.shopping

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan @SpringBootApplication class ShoppingApplication

fun main(args: Array<String>) {
    runApplication<ShoppingApplication>(*args)
}
