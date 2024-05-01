package com.example.teamcity.api.generators

object TestDataStorage {

    private val testDataList: MutableList<TestData> = mutableListOf()

    fun addTestData(): TestData {
        return addTestData(TestDataGenerator.generate())
    }

    fun addTestData(data: TestData): TestData {
        testDataList.add(data)
        return data
    }


    fun delete() {
        testDataList.forEach { it.delete() }
    }

}