package com.kelvingoodman.myRetail.service.impl;

import com.kelvingoodman.myRetail.model.RedSkyResponse;
import com.kelvingoodman.myRetail.service.RedSkyService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
                .bodyToMono(RedSkyResponse.class)
                .block();
    }
}
