package com.example.covid19tracker

class AllCovidData : ArrayList<AllCovidDataItem>()

data class AllCovidDataItem(
    val activeCases: Int,
    val country: String,
    val criticalCases: Int,
    val newCases: Int,
    val newDeaths: Int,
    val population: Int,
    val totalCases: Int,
    val totalDeaths: Int,
    val totalRecovered: Int,
    val totalTests: Int
)