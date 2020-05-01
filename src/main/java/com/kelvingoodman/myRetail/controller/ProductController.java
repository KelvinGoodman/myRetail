package com.kelvingoodman.myRetail.controller;

import com.kelvingoodman.myRetail.dao.ProductPrice;
import com.kelvingoodman.myRetail.dto.Price;
import com.kelvingoodman.myRetail.dto.Product;
import com.kelvingoodman.myRetail.repository.ProductPriceRepository;
import com.kelvingoodman.myRetail.service.RedSkyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    RedSkyService redSkyService;

    @Autowired
    ProductPriceRepository productPriceRepository;

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") int id) {
        ProductPrice productPrice = productPriceRepository.getPrice(id);

        return new Product()
                .withId(id)
                .withName(redSkyService.getProductInfo(id).getProduct().getItem().getProduct_description().getTitle())
                .withCurrent_price(new Price()
                        .withCurrency_code(productPrice.getCurrencyCode())
                        .withValue(productPrice.getPrice()));
    }
}
