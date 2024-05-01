package com.example.teamcity.api.requests.checked

import com.example.teamcity.api.models.Project
import com.example.teamcity.api.requests.CrudInterface
import com.example.teamcity.api.requests.unchecked.Request
import com.example.teamcity.api.requests.unchecked.UncheckedProject
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus.SC_OK

class CheckedProject(spec: RequestSpecification): Request(spec), CrudInterface {

    override fun create(obj: Any): Project {
        return UncheckedProject(spec).create(obj)
            .then().assertThat().statusCode(SC_OK)
            .extract().`as`(Project::class.java)
    }

    override fun get(id: String): Any {
        return UncheckedProject(spec).get(id)
            .then().assertThat().statusCode(SC_OK)
            .extract().`as`(Project::class.java)
    }

    override fun update(obj: Any): Any {
        TODO("Not yet implemented")
    }

    override fun delete(id: String): String {
        return UncheckedProject(spec)
            .delete(id = id )
            .then().assertThat().statusCode(SC_OK)
            .extract().asString()
    }
}
