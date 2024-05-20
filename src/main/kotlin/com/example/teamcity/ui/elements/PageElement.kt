package com.example.teamcity.ui.elements

import com.codeborne.selenide.SelenideElement
import org.openqa.selenium.By

abstract class PageElement(private val element: SelenideElement) {

    fun findElement(by: String) = element.find(by)

    fun findElement(by: By) = element.find(by)

    fun findElements(by: By) = element.findAll(by)
}