package com.kelvingoodman.myRetail.service.impl;

import com.kelvingoodman.myRetail.exception.GenericException;
import com.kelvingoodman.myRetail.exception.RedSkyProductNotFoundException;
import com.kelvingoodman.myRetail.model.RedSkyResponse;
import com.kelvingoodman.myRetail.service.RedSkyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RedSkyServiceImpl implements RedSkyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedSkyServiceImpl.class);

    @Value("${red.sky.hostname}")
    private String redSkyHost;

    @Value("${red.sky.path}")
    private String redSkyPath;

    /**
     * @param id of product on red sky API
     * @return product information from red sky api. Only product title is populated in response
     */
    @Override

    public RedSkyResponse getProductInfo(int id) {
        WebClient webClient = WebClient.create(redSkyHost);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(redSkyPath)
                        .build(id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse
                        -> {
                    LOGGER.error("Unable to find red sky product");
                    return Mono.error(new RedSkyProductNotFoundException());
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    LOGGER.error("Error calling red sky service");
                    return Mono.error(new GenericException());
                })
                .bodyToMono(RedSkyResponse.class)
                .block();
    }
}
