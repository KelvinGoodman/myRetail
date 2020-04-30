package com.kelvingoodman.myRetail.resource;

import com.kelvingoodman.myRetail.dto.Product;
import com.kelvingoodman.myRetail.service.impl.RedSkyServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductResource {

    @GetMapping("/product")
    public Product getProduct() {
        RedSkyServiceImpl redSkyService = new RedSkyServiceImpl();
        redSkyService.getProductInfo(1);
        return new Product();
    }
}
