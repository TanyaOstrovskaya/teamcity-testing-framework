package com.example.teamcity.ui.elements.project.create

import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.SelenideElement
import com.example.teamcity.ui.Selectors.byId
import com.example.teamcity.ui.elements.PageElement

class CreateProjectByUrlElement(element: SelenideElement): PageElement(element) {

    val urlInput by lazy { element(byId("url")) }
    val projectNameInput  by lazy { element(byId("projectName")) }
    val buildTypeNameInput by lazy {  element(byId("buildTypeName")) }
    val errorOnCreation  by lazy {  element(byId("error_url")) }

}
