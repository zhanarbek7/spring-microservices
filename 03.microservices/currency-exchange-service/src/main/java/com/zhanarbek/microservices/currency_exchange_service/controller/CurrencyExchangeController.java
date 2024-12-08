package com.zhanarbek.microservices.currency_exchange_service.controller;

import com.zhanarbek.microservices.currency_exchange_service.bean.CurrencyExchange;
import com.zhanarbek.microservices.currency_exchange_service.repository.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {
    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;
    @Autowired
    private Environment environment;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchange(
            @PathVariable String from,
            @PathVariable String to
    ){
        CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
        if (currencyExchange == null) {
            throw new RuntimeException("Unable to find data for " + from + " to " + to);
        }
        String serverPort = environment.getProperty("server.port");
        String serverName = environment.getProperty("spring.application.name");
        currencyExchange.setEnvironment(serverName + " : " + serverPort);
        return currencyExchange;

    }
}
