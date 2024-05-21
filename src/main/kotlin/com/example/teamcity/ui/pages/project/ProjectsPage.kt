package com.example.teamcity.ui.pages.project

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.Selenide.element
import com.codeborne.selenide.Selenide.elements
import com.example.teamcity.ui.Selectors.byClass
import com.example.teamcity.ui.elements.project.ProjectElement
import com.example.teamcity.ui.pages.favourites.FavouritesPage
import java.time.Duration

private const val FAVOURITE_PROJECTS = "/favorite/projects"

class ProjectsPage: FavouritesPage() {

    val firstSubProject by lazy { element(byClass("Subproject__container--Px")) }
    val subProjects by lazy { elements(byClass("Subproject__container--Px")) }

    fun open(): ProjectsPage {
        Selenide.open(FAVOURITE_PROJECTS)
        waitUntilFavouritePageLoaded()
        firstSubProject.shouldBe(Condition.visible, Duration.ofSeconds(30))
        return this
    }

    fun getSubprojects() = generatePageElements(subProjects) { el -> ProjectElement(el) }
}
