package com.example.teamcity.api.generators

import org.apache.commons.lang3.RandomStringUtils

private const val LENGTH = 10

object RandomData {
    fun getString(length: Int = LENGTH) = "test_${RandomStringUtils.randomAlphabetic(length)}".take(length)
    fun getStringLowercase(length: Int = LENGTH) = getString(length).lowercase()
}