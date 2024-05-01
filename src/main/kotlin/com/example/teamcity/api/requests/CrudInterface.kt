package com.example.teamcity.api.requests

interface CrudInterface {
//    fun create(obj: T): Any
//    fun get(id: String): T
//    fun update(obj: T): T
//    fun delete(id: String): T
    fun create(obj: Any): Any
    fun get(id: String): Any
    fun update(obj: Any): Any
    fun delete(id: String): Any
}