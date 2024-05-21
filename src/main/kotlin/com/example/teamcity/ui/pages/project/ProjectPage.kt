package com.example.teamcity.ui.pages.project

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements
import com.example.teamcity.api.models.Project
import com.example.teamcity.ui.Selectors.byClass
import com.example.teamcity.ui.Selectors.byDataHintContainerId
import com.example.teamcity.ui.elements.project.ProjectElement
import com.example.teamcity.ui.elements.project.edit.EditProjectButton
import com.example.teamcity.ui.elements.project.edit.EditProjectMenuElement
import com.example.teamcity.ui.pages.Page
import com.example.teamcity.ui.pages.project.create.CreateProjectPage
import java.time.Duration

class ProjectPage(private val project: Project): Page()  {

    private val editProjectButton by lazy { EditProjectButton(element(byDataHintContainerId("edit-entity"))) }
    private val editProjectMenuElement by lazy { EditProjectMenuElement(element(byClass("ring-list-list"))) }
    private val subProjects by lazy { elements(byClass("Subproject__container--Px")) }


    fun open(): ProjectPage {
        Selenide.open("/project/${project.id}?mode=builds")
        waitUntilPageLoaded()
        header.shouldBe(visible, Duration.ofSeconds(10))
        return this
    }

    fun openCreateSubprojectPage(): CreateProjectPage {
        editProjectButton.dropdown.click()
        editProjectMenuElement.menuOptions.forEach { it.shouldBe(visible, Duration.ofSeconds(30))}
        editProjectMenuElement.menuOptions.find { it.text() == "New subproject..." }!!.click()
        return CreateProjectPage()
    }

    fun getSubprojects() = generatePageElements(subProjects) { el -> ProjectElement(el) }
}
