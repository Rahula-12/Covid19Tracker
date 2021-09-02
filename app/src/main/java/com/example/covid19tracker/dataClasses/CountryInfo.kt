package com.example.covid19tracker

class CountryInfo : ArrayList<CountryInfoItem>()

data class CountryInfoItem(
    val code: String,
    val dial_code: String,
    val name: String
)