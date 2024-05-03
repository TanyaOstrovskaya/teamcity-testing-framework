package com.example.teamcity.api.generators

import org.apache.commons.lang3.RandomStringUtils

private const val LENGTH = 10;

object RandomData {
    fun getString() = "test_" + RandomStringUtils.randomAlphabetic(LENGTH)
    fun getStringLowercase() = getString().lowercase()
}