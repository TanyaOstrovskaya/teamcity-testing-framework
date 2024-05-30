package com.example.teamcity.ui.pages.buildtype

import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.element
import com.example.teamcity.ui.Selectors.byId
import com.example.teamcity.ui.pages.Page
import java.time.Duration.ofSeconds

class CreateBuildTypeManuallyPage: Page() {

    private val buildTypeName  by lazy { element(byId("buildTypeName")) }
    private val buildTypeId  by lazy { element(byId("buildTypeExternalId")) }
    private val errorBuildTypeName  by lazy { element(byId("error_buildTypeName")) }
    private val errorBuildTypeId  by lazy { element(byId("error_buildTypeExternalId")) }
    private val repositoryUrl  by lazy { element(byId("repositoryUrl")) }

    fun createBuildTypeManually(name: String,
                                id: String): CreateBuildTypeManuallyPage {
        buildTypeName.shouldBe(visible, ofSeconds(30)).clear()
        buildTypeName.sendKeys(name)
        buildTypeId.clear()
        buildTypeId.sendKeys(id)
        submit()
        waitCreateLoaderAbsent()
        return this
    }

    fun inputRepositoryUrl(url: String): CreateBuildTypeManuallyPage {
        repositoryUrl.shouldBe(visible, ofSeconds(10)).sendKeys(url)
        submit()
        waitCreateLoaderAbsent()
        return this
    }

    fun checkErrorBuildTypeName(msg: String): CreateBuildTypeManuallyPage {
        errorBuildTypeName.shouldBe(visible, ofSeconds(10)).has(text(msg))
        return this
    }

    fun checkErrorBuildTypeId(msg: String): CreateBuildTypeManuallyPage {
        errorBuildTypeId.shouldBe(visible, ofSeconds(10)).has(text(msg))
        return this
    }
}
