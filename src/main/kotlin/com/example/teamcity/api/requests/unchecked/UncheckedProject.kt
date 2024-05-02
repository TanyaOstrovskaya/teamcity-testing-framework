package com.example.teamcity.api.requests.unchecked

import com.example.teamcity.api.requests.CrudInterface
import com.example.teamcity.api.requests.Request
import io.restassured.RestAssured.given
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification

private const val PROJECT_ENDPOINT = "/app/rest/projects"

class UncheckedProject(spec: RequestSpecification): Request(spec), CrudInterface {

    override fun create(obj: Any): Response {
        return given()
            .spec(spec)
            .body(obj)
            .post(PROJECT_ENDPOINT)
    }

    override fun get(id: String): Response {
        return given().spec(spec).get("$PROJECT_ENDPOINT/id:$id")
    }

    override fun update(obj: Any): Any {
        TODO("Not yet implemented")
    }

    override fun delete(id: String): Response {
        return given()
            .spec(spec)
            .delete("$PROJECT_ENDPOINT/id:$id")
    }
}