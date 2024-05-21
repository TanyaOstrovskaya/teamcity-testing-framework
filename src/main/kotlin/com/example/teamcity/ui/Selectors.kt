package com.example.teamcity.ui

import com.codeborne.selenide.Selectors
import com.codeborne.selenide.selector.ByAttribute

object Selectors {

    fun byId(value: String) = ByAttribute("id", value)

    fun byType(value: String) = ByAttribute("type", value)

    fun byDataTest(value: String) = ByAttribute("data-test", value)

    fun byClass(value: String) = ByAttribute("class", value)

    fun byText(value: String) = Selectors.byText(value)

    fun byDataHintContainerId(value: String) = ByAttribute("data-hint-container-id", value)
}