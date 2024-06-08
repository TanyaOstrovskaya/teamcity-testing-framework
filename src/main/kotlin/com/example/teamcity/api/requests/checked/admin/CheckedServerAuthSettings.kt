package com.example.teamcity.api.requests.checked.admin

import com.example.teamcity.api.models.ServerAuthSettings
import com.example.teamcity.api.requests.Request
import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured
import io.restassured.RestAssured
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus.SC_OK

private const val AUTH_SETTINGS_ENDPOINT = "/app/rest/server/authSettings"

class CheckedServerAuthSettings(spec: RequestSpecification): Request(spec) {

    fun get(): ServerAuthSettings {
        return RestAssured.given()
            .filter(SwaggerCoverageRestAssured())
            .spec(spec).get(AUTH_SETTINGS_ENDPOINT)
            .then().assertThat().statusCode(SC_OK)
            .extract().`as`(ServerAuthSettings::class.java)
    }

    fun update(settings: ServerAuthSettings): String {
        return RestAssured.given()
            .filter(SwaggerCoverageRestAssured())
            .spec(spec)
            .body(settings)
            .put(AUTH_SETTINGS_ENDPOINT)
                .then().assertThat().statusCode(SC_OK)
                .extract().asString()
    }
}