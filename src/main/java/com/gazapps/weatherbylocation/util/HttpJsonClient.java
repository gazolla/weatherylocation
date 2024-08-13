package com.gazapps.weatherbylocation.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Singleton HttpJsonClient
 * 
 * <p>Esta classe é responsável por realizar requisições HTTP e deserializar respostas JSON em objetos Java. 
 * Ela utiliza o padrão de projeto Singleton na forma de uma enumeração para garantir que apenas uma instância da 
 * classe esteja disponível em todo o sistema.</p>
 * 
 * <p>A principal funcionalidade desta classe é o método genérico `extractData`, que faz uma requisição GET para uma URL 
 * fornecida, obtém a resposta JSON, e a converte em um objeto da classe especificada. Esse método é útil para 
 * comunicação com APIs RESTful que retornam dados em formato JSON.</p>
 * 
 * <p>Exemplo de uso:</p>
 * 
 * <pre>
 * {@code
 * String url = "https://api.example.com/data";
 * ResponseClass response = HttpJsonClient.INSTANCE.extractData(url, ResponseClass.class);
 * }
 * </pre>
 * 
 * @author [Sebastiao Gazolla C Jr]
 * @version 1.0
 * @since 2024
 */

public enum HttpJsonClient {

	INSTANCE;
	
	public <T> CompletableFuture<T> extractDataAsync(String url, Class<T> responseType) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        ObjectMapper mapper = new ObjectMapper();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    try {
                        String json = response.body();
                        return mapper.readValue(json, responseType);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                });
    }


}