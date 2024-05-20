package com.example.teamcity.ui

import com.codeborne.selenide.selector.ByAttribute

object Selectors {

    fun byId(value: String) = ByAttribute("id", value)

    fun byType(value: String) = ByAttribute("type", value)

    fun byDataTest(value: String) = ByAttribute("data-test", value)

    fun byClass(value: String) = ByAttribute("class", value)
}