package com.example.teamcity.api.requests.unchecked

import com.example.teamcity.api.requests.CrudInterface
import com.example.teamcity.api.requests.Request
import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured
import io.restassured.RestAssured.given
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification

private const val USER_ENDPOINT = "/app/rest/users"

class UncheckedUser(spec: RequestSpecification): Request(spec), CrudInterface {

    override fun create(obj: Any): Response {
        return given()
            .filter(SwaggerCoverageRestAssured())
            .spec(spec)
            .body(obj)
            .post(USER_ENDPOINT)
    }

    override fun get(id: String): Any {
        TODO("Not yet implemented")
    }

    override fun update(obj: Any): Any {
        TODO("Not yet implemented")
    }

    override fun delete(username: String): Response {
        return given()
            .filter(SwaggerCoverageRestAssured())
            .spec(spec).delete("$USER_ENDPOINT/username:$username")
    }
}