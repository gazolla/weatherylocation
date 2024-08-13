package com.gazapps.weatherbylocation.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseGeoLocation(
		@JsonProperty("type") String type,
		@JsonProperty("query") List<String> query,
		@JsonProperty("features") List<Feature> features,
		@JsonProperty("attribution") String attribution
		) {
	private String nonNull(String value, String defaultValue) {
		return value != null ? value : defaultValue;
	}

	private String nonNull(String value) {
		return nonNull(value, "N/A");
	}

	@Override
	public String toString() {
		if (!this.features().isEmpty()) {
			Feature feature = this.features().get(0);
			return String.format(
					"A localização '%s' é encontrada em '%s'.\n" +
							"Tipo: %s\n" +
							"Relevância: %.2f\n" +
							"Coordenadas: Latitude %.6f, Longitude %.6f\n" +
							"Contexto: %s, %s",
							nonNull(feature.text(), "N/A"),
							nonNull(feature.placeName(), "N/A"),
							nonNull(feature.placeType() != null && !feature.placeType().isEmpty() ? feature.placeType().get(0) : "N/A"),
							feature.relevance() != null ? feature.relevance() : 0.0,
							feature.center() != null && feature.center().size() > 1 ? feature.center().get(1) : 0.0,
							feature.center() != null && feature.center().size() > 0 ? feature.center().get(0) : 0.0,
							nonNull(feature.context() != null && feature.context().size() > 0 ? feature.context().get(0).text() : "N/A"),
							nonNull(feature.context() != null && feature.context().size() > 1 ? feature.context().get(1).text() : "N/A")
					);
		} else {
			return "No features found.";
		}
	}

	public double getLatitude() {
		if (this.features().isEmpty()) return 0.0;
		Feature feature = this.features().get(0);
		return feature.center() != null && feature.center().size() > 1 ? feature.center().get(1) : 0.0;
	}
	public double getLongitude() {
		if (this.features().isEmpty()) return 0.0;
		Feature feature = this.features().get(0);
		return feature.center() != null && feature.center().size() > 1 ? feature.center().get(0) : 0.0;
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
record Feature(
	    @JsonProperty("id") String id,
	    @JsonProperty("type") String type,
	    @JsonProperty("place_type") List<String> placeType,
	    @JsonProperty("relevance") Double relevance,
	    @JsonProperty("properties") Properties properties,
	    @JsonProperty("text") String text,
	    @JsonProperty("place_name") String placeName,
	    @JsonProperty("bbox") List<Double> bbox,
	    @JsonProperty("center") List<Double> center,
	    @JsonProperty("geometry") Geometry geometry,
	    @JsonProperty("context") List<Context> context
	) {}

@JsonIgnoreProperties(ignoreUnknown = true)
record Properties(
		@JsonProperty("mapbox_id") String mapboxId,
		@JsonProperty("wikidata") String wikidata,
		@JsonProperty("short_code") String shortCode,
		@JsonProperty("foursquare") String foursquare,
		@JsonProperty("landmark") String landmark,
		@JsonProperty("address") String address,
		@JsonProperty("category") String category,
		@JsonProperty("accuracy") String accuracy

		) {}

record Geometry(
		@JsonProperty("type") String type,
		@JsonProperty("coordinates") List<Double> coordinates,
		@JsonProperty("interpolated") List<Double> interpolated
		) {}

record Context(
		@JsonProperty("id") String id,
		@JsonProperty("mapbox_id") String mapboxId,
		@JsonProperty("wikidata") String wikidata,
		@JsonProperty("short_code") String shortCode,
		@JsonProperty("text") String text
		) {}
