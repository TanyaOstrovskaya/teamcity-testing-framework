package com.example.teamcity.api.requests.checked

import com.example.teamcity.api.models.BuildType
import com.example.teamcity.api.requests.CrudInterface
import com.example.teamcity.api.requests.Request
import com.example.teamcity.api.requests.unchecked.UncheckedBuildConfig
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus.SC_NO_CONTENT
import org.apache.http.HttpStatus.SC_OK

class CheckedBuildConfig(spec: RequestSpecification): Request(spec), CrudInterface {

    override fun create(obj: Any): BuildType {
        return UncheckedBuildConfig(spec).create(obj)
            .then().assertThat().statusCode(SC_OK)
            .extract().`as`(BuildType::class.java)
    }

    override fun get(id: String): Any {
        TODO("Not yet implemented")
    }

    override fun update(obj: Any): Any {
        TODO("Not yet implemented")
    }

    override fun delete(id: String): Any {
        return UncheckedBuildConfig(spec).delete(id)
            .then().assertThat().statusCode(SC_NO_CONTENT)
            .extract().asString()
    }
}