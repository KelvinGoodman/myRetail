package com.kelvingoodman.myRetail.controller;

import com.kelvingoodman.myRetail.dto.Price;
import com.kelvingoodman.myRetail.dto.Product;
import com.kelvingoodman.myRetail.service.RedSkyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@RestController
public class ProductController {

    @Autowired
    RedSkyService redSkyService;

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") @NotBlank @Size(max = 10) int id) {
        System.out.println(id);
        return new Product()
                .withId(id)
                .withName(redSkyService.getProductInfo(id).getProduct().getItem().getProduct_description().getTitle())
                .withCurrent_price(new Price().withCurrency_code("USD").withValue(BigDecimal.valueOf(100)));
    }
}
