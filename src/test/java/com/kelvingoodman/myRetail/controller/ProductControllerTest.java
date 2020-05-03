package com.kelvingoodman.myRetail.controller;

import com.kelvingoodman.myRetail.dao.ProductPrice;
import com.kelvingoodman.myRetail.dto.Product;
import com.kelvingoodman.myRetail.model.Item;
import com.kelvingoodman.myRetail.model.ProductDescription;
import com.kelvingoodman.myRetail.model.RedSkyResponse;
import com.kelvingoodman.myRetail.repository.ProductPriceRepository;
import com.kelvingoodman.myRetail.service.RedSkyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @Mock
    RedSkyService redSkyService;

    @Mock
    ProductPriceRepository productPriceRepository;

    @InjectMocks
    ProductController productController;

    @BeforeEach
    void setup() {
        Optional<ProductPrice> productPrice = Optional.of(new ProductPrice()
                .withId(123)
                .withPrice(BigDecimal.valueOf(99.99))
                .withCurrencyCode("USD"));

        Mockito.when(productPriceRepository.findById(Integer.valueOf(123))).thenReturn(productPrice);

        ProductDescription productDescription = new ProductDescription().withTitle("An Amazing Product");
        Item item = new Item().withProductDescription(productDescription);
        com.kelvingoodman.myRetail.model.Product product = new com.kelvingoodman.myRetail.model.Product().withItem(item);
        RedSkyResponse redSkyResponse = new RedSkyResponse().withProduct(product);

        Mockito.when(redSkyService.getProductInfo(123)).thenReturn(redSkyResponse);
    }

    @Test
    void happyPath() {
        Product product = productController.getProduct(123);
        assertEquals(BigDecimal.valueOf(99.99), product.getCurrent_price().getValue());
        assertEquals("An Amazing Product", product.getName());
    }
    
}
