package com.kelvingoodman.myRetail.resource;

import com.kelvingoodman.myRetail.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductResource {

    @GetMapping("/product")
    public Product getProduct() {
        return new Product();
    }
}
