package com.example.teamcity.api.requests.checked

import com.example.teamcity.api.models.User
import com.example.teamcity.api.requests.CrudInterface
import com.example.teamcity.api.requests.Request
import com.example.teamcity.api.requests.unchecked.UncheckedUser
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus

class CheckedUser(spec: RequestSpecification): Request(spec), CrudInterface {
    override fun create(obj: Any): User {
        return UncheckedUser(spec).create(obj)
            .then().assertThat().statusCode(HttpStatus.SC_OK)
            .extract().`as`(User::class.java)
    }

    override fun get(id: String): Any {
        TODO("Not yet implemented")
    }

    override fun update(obj: Any): Any {
        TODO("Not yet implemented")
    }

    override fun delete(id: String): String {
        return UncheckedUser(spec).delete(id)
            .then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
            .extract().asString()
    }
}