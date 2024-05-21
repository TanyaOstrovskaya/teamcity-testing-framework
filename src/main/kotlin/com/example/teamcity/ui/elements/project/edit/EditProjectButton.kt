package com.example.teamcity.ui.elements.project.edit

import com.codeborne.selenide.SelenideElement
import com.example.teamcity.ui.Selectors.byClass
import com.example.teamcity.ui.Selectors.byDataTest
import com.example.teamcity.ui.elements.PageElement


class EditProjectButton(element: SelenideElement): PageElement(element) {
    val content by lazy { findElement(byClass("ring-button-content")) }
    val dropdown by lazy {  findElement(byDataTest("ring-dropdown")) }
}
