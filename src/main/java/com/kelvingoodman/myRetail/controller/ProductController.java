package com.kelvingoodman.myRetail.controller;

import com.kelvingoodman.myRetail.dao.ProductPrice;
import com.kelvingoodman.myRetail.dto.Price;
import com.kelvingoodman.myRetail.dto.Product;
import com.kelvingoodman.myRetail.exception.ProductPriceNotFoundException;
import com.kelvingoodman.myRetail.model.RedSkyResponse;
import com.kelvingoodman.myRetail.repository.ProductPriceRepository;
import com.kelvingoodman.myRetail.service.RedSkyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Validated
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    RedSkyService redSkyService;
    @Autowired
    ProductPriceRepository productPriceRepository;

    /***
     * Test database is populated with date for product ids between 13860416 and 13860433, so I've restricted inputs
     * to this range
     * @param id
     * @return Product
     */
    @GetMapping(value = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getProduct(@PathVariable("id") @Min(0) int id) {
        try {
            RedSkyResponse redSkyResponse = redSkyService.getProductInfo(id);
            ProductPrice productPrice = productPriceRepository.findById(id).orElseThrow(() -> new ProductPriceNotFoundException());
            return new Product().withId(id)
                    .withName(redSkyResponse.getProduct().getItem().getProduct_description().getTitle())
                    .withCurrent_price(new Price().withCurrency_code(productPrice.getCurrencyCode()).withValue(productPrice.getPrice()));
        } catch (ProductPriceNotFoundException ex) {
            LOGGER.error("Unable to find product price");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @PostMapping(value = "/product/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProduct(@Valid @RequestBody ProductPrice productPrice) {
        productPriceRepository.save(productPrice);
    }
}
