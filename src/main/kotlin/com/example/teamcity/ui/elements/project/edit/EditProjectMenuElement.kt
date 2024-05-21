package com.example.teamcity.ui.elements.project.edit

import com.codeborne.selenide.SelenideElement
import com.example.teamcity.ui.Selectors.byDataTest
import com.example.teamcity.ui.elements.PageElement

class EditProjectMenuElement (element: SelenideElement): PageElement(element) {
    val menuOptions by lazy { findElements(byDataTest("ring-link ring-list-link ring-list-item")) }
}
