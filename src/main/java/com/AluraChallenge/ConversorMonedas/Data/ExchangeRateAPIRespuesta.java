package com.AluraChallenge.ConversorMonedas.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExchangeRateAPIRespuesta(
        Double conversion_rate
)
{}
