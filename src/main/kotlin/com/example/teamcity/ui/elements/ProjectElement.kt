package com.example.teamcity.ui.elements

import com.codeborne.selenide.SelenideElement
import com.example.teamcity.ui.Selectors.byDataTest

class ProjectElement(element: SelenideElement): PageElement(element) {

    val header: SelenideElement = findElement(byDataTest("subproject"))
    val icon: SelenideElement = findElement("svg")
}