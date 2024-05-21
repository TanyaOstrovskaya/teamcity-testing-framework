package com.example.teamcity.ui.elements.project.create

import com.codeborne.selenide.SelenideElement
import com.example.teamcity.ui.Selectors.byId
import com.example.teamcity.ui.elements.PageElement

class CreateProjectManuallyElement(element: SelenideElement): PageElement(element) {
    val projectName  by lazy { findElement(byId("name")) }
    val projectId  by lazy { findElement(byId("externalId")) }
}
