package com.proalekse1.wetherapp7.adapters

data class WeatherModel (
    val city: String,
    val time: String,
    val condition: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val imageUrl: String,
    val hours: String //будет брать погоду по часам из переданного json формата с сайта

    )