package com.kelvingoodman.myRetail.controller;

import com.kelvingoodman.myRetail.dao.ProductPrice;
import com.kelvingoodman.myRetail.dto.Price;
import com.kelvingoodman.myRetail.dto.Product;

import com.kelvingoodman.myRetail.exception.ProductPriceNotFoundException;
import com.kelvingoodman.myRetail.exception.RedSkyProductNotFoundException;
import com.kelvingoodman.myRetail.model.RedSkyResponse;
import com.kelvingoodman.myRetail.repository.ProductPriceRepository;
import com.kelvingoodman.myRetail.service.RedSkyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ProductController {

    @Autowired
    RedSkyService redSkyService;

    @Autowired
    ProductPriceRepository productPriceRepository;

    @GetMapping(value = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getProduct(@PathVariable("id") int id) {
        try {
            RedSkyResponse redSkyResponse = redSkyService.getProductInfo(id);
            ProductPrice productPrice = productPriceRepository.findById(id).orElseThrow(() -> new ProductPriceNotFoundException());

            return new Product().withId(id)
                    .withName(redSkyResponse.getProduct().getItem().getProduct_description().getTitle())
                    .withCurrent_price(new Price().withCurrency_code(productPrice.getCurrencyCode()).withValue(productPrice.getPrice()));
        } catch (ProductPriceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,ex.getMessage(), ex);
        }
    }
}
