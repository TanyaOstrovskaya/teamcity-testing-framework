package com.example.teamcity.ui.pages

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements
import com.codeborne.selenide.SelenideElement
import com.example.teamcity.ui.Selectors.byClass
import com.example.teamcity.ui.elements.ProjectElement
import com.example.teamcity.ui.pages.favourites.FavouritesPage
import java.time.Duration

const val FAVOURITE_PROJECTS = "/favorite/projects"

class ProjectsPage: FavouritesPage() {

    val firstSubProject: SelenideElement by lazy {  element(byClass("Subproject__container--Px"))}
    val subProjects: ElementsCollection by lazy { elements(byClass("Subproject__container--Px"))}

    fun open(): ProjectsPage {
        Selenide.open(FAVOURITE_PROJECTS)
        waitUntilFavouritePageLoaded()
        firstSubProject.shouldBe(visible, Duration.ofSeconds(30))
        return this
    }

    fun getSubprojects() = generatePageElements(subProjects) { el -> ProjectElement(el) }
}
