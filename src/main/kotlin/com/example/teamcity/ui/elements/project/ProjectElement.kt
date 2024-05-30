package com.example.teamcity.ui.elements.project

import com.codeborne.selenide.SelenideElement
import com.example.teamcity.ui.Selectors.byDataTest
import com.example.teamcity.ui.elements.PageElement

class ProjectElement(element: SelenideElement): PageElement(element) {

    val header: SelenideElement = findElement(byDataTest("subproject"))
    val icon: SelenideElement = findElement("svg")
}