package com.example.teamcity.api.requests.unchecked

import com.example.teamcity.api.requests.CrudInterface
import io.restassured.RestAssured.given
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification

private const val BUILD_CONFIG_ENDPOINT = "/app/rest/buildTypes"

class UncheckedBuildConfig(spec: RequestSpecification): Request(spec), CrudInterface {

    override fun create(obj: Any): Response {
        return given()
            .spec(spec)
            .body(obj)
            .post(BUILD_CONFIG_ENDPOINT)
    }

    override fun get(id: String): Any {
        TODO("Not yet implemented")
    }

    override fun update(obj: Any): Any {
        TODO("Not yet implemented")
    }

    override fun delete(id: String): Response {
        return given()
            .spec(spec)
            .delete("$BUILD_CONFIG_ENDPOINT/id:$id")
    }
}