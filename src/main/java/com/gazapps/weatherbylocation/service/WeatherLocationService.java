package com.gazapps.weatherbylocation.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import com.gazapps.weatherbylocation.model.ResponseGeoLocation;
import com.gazapps.weatherbylocation.model.ResponseWeather;
import com.gazapps.weatherbylocation.util.HttpJsonClient;

/**
 * Serviço de localização do tempo que utiliza o padrão Facade e é implementado como um singleton utilizando um enum.
 * 
 * Esta classe fornece um método para obter as condições climáticas atuais com base em uma localização fornecida. 
 * Ela integra dois serviços web:
 * 1. **Mapbox Geocoding API** - para converter a localização fornecida em coordenadas geográficas (latitude e longitude).
 * 2. **Weatherstack API** - para recuperar as condições climáticas atuais usando as coordenadas obtidas.
 * 
 * A classe é implementada como um singleton usando um enum, garantindo que apenas uma instância do serviço esteja disponível.
 * 
 * Exemplo de uso:
 * <pre>
 * String resultado = WeatherLocationService.INSTANCE.getWeatherByLocation("São Paulo");
 * System.out.println(resultado);
 * </pre>
 * 
 * @see HttpJsonClient
 * @see ResponseGeoLocation
 * @see ResponseWeather
 * 
 * @author [Sebastiao Gazolla C Jr]
 * @version 1.0
 * @since 2024
 */
public enum WeatherLocationService {
	
	INSTANCE;
	
	private final String CONFIG_FILE = "src/main/resources/config.properties";
	private static final AtomicReference<String> geoLocationApiKey = new AtomicReference<>();
	private static final AtomicReference<String> weatherApiKey = new AtomicReference<>();
	
	private void config() throws IOException{
		Properties prop = new Properties();
		try (var reader = new BufferedReader(new FileReader(CONFIG_FILE))) {
			prop.load(reader);
			geoLocationApiKey.set(prop.getProperty("geoLocationApiKey"));
			weatherApiKey.set(prop.getProperty("weatherApiKey"));
			if (geoLocationApiKey.get() == null) {
				throw new RuntimeException("Falta API key do geoLocation no arquivo config.properties");
			}
			if (weatherApiKey.get() == null) {
				throw new RuntimeException("Falta API key do Weather no arquivo config.properties");
			}
		}
	}

	public CompletableFuture<String> getWeatherByLocationAsync(String location) throws UnsupportedEncodingException, IOException {
		if (geoLocationApiKey.get() == null || weatherApiKey.get() == null) config();

		if (location.isEmpty() || location.isBlank() || location == null) return CompletableFuture.completedFuture("Localidade vazia.");
        
        location = URLEncoder.encode(location, "UTF-8");

        var limit = "1";
        var urlGeoLocation = String.format("https://api.mapbox.com/geocoding/v5/mapbox.places/%s.json?access_token=%s&limit=%s", location, geoLocationApiKey, limit);

        // Faz a requisição de geolocalização de forma assíncrona
        return HttpJsonClient.INSTANCE.extractDataAsync(urlGeoLocation, ResponseGeoLocation.class)
            .thenCompose(geoLocation -> {
                if (geoLocation == null || geoLocation.features().isEmpty()) {
                    return CompletableFuture.completedFuture("Não Encontrado.");
                }

                var latitude = geoLocation.getLatitude();
                var longitude = geoLocation.getLongitude();
                String urlWeather = String.format("https://api.weatherstack.com/current?access_key=%s&query=%s,%s", weatherApiKey, latitude, longitude);

                // Faz a requisição do clima de forma assíncrona
                return HttpJsonClient.INSTANCE.extractDataAsync(urlWeather, ResponseWeather.class)
                    .thenApply(weather -> {
                        if (weather == null) {
                            return "Não foi possível obter as condições meteorológicas.";
                        }
                        return String.format("%s\n%s", geoLocation.toString(), weather.toString());
                    });
            });
    }

}
