
# Desafio de Projeto 

Este repositório contém um aplicativo de consulta de condições climáticas que permite aos usuários obter informações meteorológicas atuais para qualquer cidade. 
Criado para o curso `Design Patterns com Java: Dos Clássicos (GoF) ao Spring Framework` da [DIO](https://web.dio.me/)

## Funcionalidades

- **Interação com o Usuário:** O aplicativo solicita ao usuário que insira o nome de uma cidade e exibe as condições climáticas atuais para a localização fornecida.
- **Encerramento Simples:** O usuário pode sair do aplicativo a qualquer momento digitando "sair".
- **Requisição de Dados Meteorológicos:** Utiliza o serviço `WeatherLocationService` para buscar e exibir os dados meteorológicos da cidade especificada.

## Fluxo de Uso

1. **Solicitação de Cidade:** Ao iniciar o aplicativo, ele pede ao usuário que insira o nome da cidade desejada.
2. **Processamento da Entrada:** O nome da cidade é enviado ao serviço meteorológico para consulta.
3. **Exibição de Resultados:** As condições climáticas atuais para a cidade são exibidas ao usuário.
4. **Opção de Encerramento:** O processo repete até que o usuário digite "sair" para encerrar o programa.

Este aplicativo é uma ferramenta simples e eficiente para obter informações meteorológicas de forma interativa através do terminal.

<img src="https://raw.githubusercontent.com/gazolla/weatherylocation/main/weather.gif" width="440">

### Uso

- Para usar esse aplicativo, você precisará incluir a chave da API gerada no site da `weatherstack` [aqui](https://weatherstack.com/) no arquivo config.properties.

- Você também precisará incluir a chave da API gerada no site da `mapbox` [aqui](https://www.mapbox.com/) no arquivo config.properties.