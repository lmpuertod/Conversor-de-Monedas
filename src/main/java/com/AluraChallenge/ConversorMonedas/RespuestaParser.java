package com.AluraChallenge.ConversorMonedas;

import com.AluraChallenge.ConversorMonedas.Data.ExchangeRateAPIRespuesta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RespuestaParser {

    public Double obtenerTasaCambio(String jsonResponse) throws JsonProcessingException {

        ObjectMapper parser = new ObjectMapper();
        return parser.readValue(jsonResponse, ExchangeRateAPIRespuesta.class).conversion_rate();

    }


    
}
