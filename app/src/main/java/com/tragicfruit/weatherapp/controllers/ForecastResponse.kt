package com.tragicfruit.weatherapp.controllers

import com.google.gson.annotations.SerializedName

data class ForecastResponse(val city: City, val cnt: Int, val list: List<Item>) {

    data class City(val id: Int, val name: String, val coord: Coord, val country: String) {
        data class Coord(val lat: Double, val lon: Double)
    }

    data class Item(val dt: Long,
                    val main: Main?,
                    val weather: List<Weather>?,
                    val clouds: Clouds?,
                    val wind: Wind?,
                    val rain: Rain?,
                    val snow: Snow?) {

        data class Main(val temp: Double, val temp_min: Double, val temp_max: Double, val pressure: Double,
                        val sea_level: Double, val grnd_level: Double, val humidity: Int)
        data class Weather(val id: Int, val main: String, val description: String, val icon: String)
        data class Clouds(val all: Int)
        data class Wind(val speed: Double, val deg: Double)
        data class Rain(@SerializedName("3h") val threeHour: Double)
        data class Snow(@SerializedName("3h") val threeHour: Double)
    }
}
