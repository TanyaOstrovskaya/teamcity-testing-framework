package com.example.teamcity.api.spec

import com.example.teamcity.api.config.Config
import com.example.teamcity.api.models.User
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification

object Specifications  {

    private fun reqBuilder(): RequestSpecBuilder {
        val requestBuilder = RequestSpecBuilder()
        requestBuilder.setBaseUri("http://${Config.getProperty("host")}")
        requestBuilder.addFilter(RequestLoggingFilter())
        requestBuilder.addFilter(ResponseLoggingFilter())
        requestBuilder.setContentType(ContentType.JSON)
        requestBuilder.setAccept(ContentType.JSON)
        return requestBuilder
    }

    fun unauthSpec(): RequestSpecification {
        val requestBuilder = reqBuilder()
        return requestBuilder.build()
    }

    fun authSpec(user: User): RequestSpecification {
        val requestBuilder = reqBuilder()
        requestBuilder.setBaseUri("http://${user.username}:${user.password}@${Config.getProperty("host")}")
        return requestBuilder.build()
    }

    fun superUserSpec(): RequestSpecification {
        val requestBuilder = reqBuilder()
        requestBuilder.setBaseUri("http://:${Config.getProperty("superUserToken")}@${Config.getProperty("host")}")
        return requestBuilder.build()
    }
}