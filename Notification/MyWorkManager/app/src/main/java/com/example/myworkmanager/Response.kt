package com.example.myworkmanager

import com.squareup.moshi.Json

data class Response(
	@Json(name="main")
	val main: Main,

	@Json(name="weather")
	val weather: List<WeatherItem>,

	@Json(name="name")
	val name: String? = null,

	@Json(name="id")
	val id: Int? = null
)

data class WeatherItem(
	@Json(name="description")
	val description: String? = null,

	@Json(name="main")
	val main: String
)

data class Main(
	@Json(name="temp")
	val temp: Double
)
