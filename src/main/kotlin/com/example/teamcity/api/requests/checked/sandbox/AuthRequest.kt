package com.example.teamcity.api.requests.checked.sandbox

import com.example.teamcity.api.models.User
import com.example.teamcity.api.spec.Specifications
import io.restassured.RestAssured
import org.apache.http.HttpStatus.SC_OK

class AuthRequest(val user: User) {

    fun getCsrToken() = RestAssured
        .given()
        .spec(Specifications.authSpec(user))
        .get("/authenticationTest.html?csrf")
        .then().assertThat().statusCode(SC_OK)
        .extract().asString();

}