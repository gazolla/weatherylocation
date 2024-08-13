package com.gazapps.weatherbylocation.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseWeather(
		@JsonProperty("request") Request request,
		@JsonProperty("location") Location location,
		@JsonProperty("current") CurrentWeather current
		) {
	@Override
	public String toString() {
		Location location = this.location();
		CurrentWeather current = this.current();

		return String.format("A temperatura em %s, %s é de %d°C. O clima está %s com sensação de %d°C. "
				+ "A velocidade do vento é de %d km/h, com uma pressão atmosférica de %d hPa e umidade de %d%%. "
				+ "O índice UV é %d e a visibilidade é de %d km. Observado às %s.",
				location.name(), location.region(), current.temperature(), 
				String.join(", ", current.weatherDescriptions()), 
				current.feelslike(), current.windSpeed(), current.pressure(), 
				current.humidity(), current.uvIndex(), current.visibility(), 
				current.observationTime());
	}
}


record Request(
		@JsonProperty("type") String type,
		@JsonProperty("query") String query,
		@JsonProperty("language") String language,
		@JsonProperty("unit") String unit
		) {}

record Location(
		@JsonProperty("name") String name,
		@JsonProperty("country") String country,
		@JsonProperty("region") String region,
		@JsonProperty("lat") String lat,
		@JsonProperty("lon") String lon,
		@JsonProperty("timezone_id") String timezoneId,
		@JsonProperty("localtime") String localtime,
		@JsonProperty("localtime_epoch") Integer localtimeEpoch,
		@JsonProperty("utc_offset") String utcOffset
		) {}

record CurrentWeather(
		@JsonProperty("observation_time") String observationTime,
		@JsonProperty("temperature") Integer temperature,
		@JsonProperty("weather_code") Integer weatherCode,
		@JsonProperty("weather_icons") List<String> weatherIcons,
		@JsonProperty("weather_descriptions") List<String> weatherDescriptions,
		@JsonProperty("wind_speed") Integer windSpeed,
		@JsonProperty("wind_degree") Integer windDegree,
		@JsonProperty("wind_dir") String windDir,
		@JsonProperty("pressure") Integer pressure,
		@JsonProperty("precip") Integer precip,
		@JsonProperty("humidity") Integer humidity,
		@JsonProperty("cloudcover") Integer cloudcover,
		@JsonProperty("feelslike") Integer feelslike,
		@JsonProperty("uv_index") Integer uvIndex,
		@JsonProperty("visibility") Integer visibility,
		@JsonProperty("is_day") String isDay
		) {}

