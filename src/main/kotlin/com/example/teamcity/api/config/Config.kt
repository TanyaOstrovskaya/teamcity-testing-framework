package com.example.teamcity.api.config

import java.util.Properties

private const val CONFIG_PROPERTIES = "config.properties"

object Config {

    private val properties: Properties = Properties()

    init {
        loadProperties(CONFIG_PROPERTIES)
    }

    private fun loadProperties(fileName: String) {
        runCatching {
            val inputStream = javaClass.classLoader.getResourceAsStream(fileName)
            checkNotNull(inputStream) { " File not found: $fileName" }
            properties.load(inputStream)
        }.onSuccess {
            println("Loaded properties from $fileName")
        }.onFailure {
            println("Error during file reading: ${it.message}")
        }
    }

    fun getProperty(key: String) = properties.getProperty(key)
}