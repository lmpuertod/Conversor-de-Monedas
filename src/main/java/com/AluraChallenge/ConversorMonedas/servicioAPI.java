package com.AluraChallenge.ConversorMonedas;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class servicioAPI {

    private static final HttpClient cliente = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();
    public String obtenerTasaCambio(String monedaBase, String monedaObjetivo, String API_KEY) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(STR."https://v6.exchangerate-api.com/v6/\{API_KEY}/pair/\{monedaBase}/\{monedaObjetivo}"))
                .GET()
                .build();
        return cliente.send(request, HttpResponse.BodyHandlers.ofString())
                .body();

    }
}
