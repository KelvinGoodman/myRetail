package com.kelvingoodman.myRetail.service.impl;

import com.kelvingoodman.myRetail.exception.GenericException;
import com.kelvingoodman.myRetail.exception.RedSkyProductNotFoundException;
import com.kelvingoodman.myRetail.model.RedSkyResponse;
import com.kelvingoodman.myRetail.service.RedSkyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RedSkyServiceImpl implements RedSkyService {

    @Override
    public RedSkyResponse getProductInfo(int id) {
        WebClient webClient = WebClient.create("https://redsky.target.com");

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/pdp/tcin/{id}")
                        .build(id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new RedSkyProductNotFoundException()))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new GenericException()))
                .bodyToMono(RedSkyResponse.class)
                .block();
    }
}
