package com.proalekse1.weatherapp7

data class DayItem( //дата класс
    val city: String, //название города
    val time: String, //время погоды
    val condition: String, //название погоды погоды, солнечно дождливо
    val imageUrl: String, // ссылка на картинку с погодой солнышко тучка
    val currentTemp: String, //текущая температура
    val maxTemp: String, //максимальная температура
    val minTemp: String, //минимальная температура
    val hours: String //время для прогноза по часам
)