package com.gazapps.weatherbylocation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

import com.gazapps.weatherbylocation.service.WeatherLocationService;

/**
 * Classe principal para execução do aplicativo de consulta de condições climáticas.
 * 
 * Esta classe contém o método `main` que serve como ponto de entrada para o aplicativo. 
 * O aplicativo solicita ao usuário que insira o nome de uma cidade e, em resposta, exibe as condições climáticas atuais para essa localização. 
 * O usuário pode encerrar o aplicativo digitando "sair".
 * 
 * O fluxo do programa é o seguinte:
 * 1. Exibe uma mensagem inicial solicitando o nome da cidade.
 * 2. Recebe a entrada do usuário.
 * 3. Se o usuário digitar "sair", o aplicativo exibe uma mensagem de despedida e encerra.
 * 4. Caso contrário, o aplicativo utiliza o serviço `WeatherLocationService` para obter e exibir as condições climáticas para a cidade fornecida.
 * 5. O processo se repete até que o usuário decida sair.
 * 
 * 
 * @throws UnsupportedEncodingException Se ocorrer um erro ao codificar a localização para a URL.
 */
public class App 
{
	public static void main( String[] args ) throws UnsupportedEncodingException, IOException
	{
		try (Scanner scan = new Scanner(System.in)){
			String ask = "";
			while (true) {
				System.out.println("Condições do Tempo para hoje");
				System.out.println("Digite <<sair>> para sair ou ");
				System.out.println("Digite o nome da cidade:");
				ask = scan.nextLine();

				if (ask.equals("sair")) {
					System.out.println("tchau");
					System.exit(0);
				}

				CompletableFuture<String> futureResult = WeatherLocationService.INSTANCE.getWeatherByLocationAsync(ask);
				try {
					String result = futureResult.join(); 
					System.out.println(result);
				} catch (Exception ex) {
					System.err.println("Erro ao obter o clima: " + ex.getMessage());
				}
				System.out.println("-------------------------------------------------------");
				System.out.println("");
			}

		}
	}
}
