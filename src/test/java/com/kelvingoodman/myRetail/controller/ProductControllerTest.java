package com.kelvingoodman.myRetail.controller;

import com.kelvingoodman.myRetail.dao.ProductPrice;
import com.kelvingoodman.myRetail.dto.Product;
import com.kelvingoodman.myRetail.model.Item;
import com.kelvingoodman.myRetail.model.ProductDescription;
import com.kelvingoodman.myRetail.model.RedSkyResponse;
import com.kelvingoodman.myRetail.repository.ProductPriceRepository;
import com.kelvingoodman.myRetail.service.RedSkyService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    private static RedSkyResponse happyRedSkyResponse;
    private static Optional<ProductPrice> happyProductPrice;
    @Mock
    RedSkyService redSkyService;
    @Mock
    ProductPriceRepository productPriceRepository;
    @InjectMocks
    ProductController productController;

    @BeforeAll
    static void setup() {
        happyProductPrice = Optional.of(new ProductPrice()
                .withId(123)
                .withPrice(BigDecimal.valueOf(99.99))
                .withCurrencyCode("USD"));

        ProductDescription productDescription = new ProductDescription().withTitle("An Amazing Product");
        Item item = new Item().withProductDescription(productDescription);
        com.kelvingoodman.myRetail.model.Product product = new com.kelvingoodman.myRetail.model.Product().withItem(item);
        happyRedSkyResponse = new RedSkyResponse().withProduct(product);
    }

    @Test
    void happyPath() {
        Mockito.when(productPriceRepository.findById(123)).thenReturn(happyProductPrice);
        Mockito.when(redSkyService.getProductInfo(123)).thenReturn(happyRedSkyResponse);
        Product product = productController.getProduct(123);
        assertEquals(BigDecimal.valueOf(99.99), product.getCurrent_price().getValue());
        assertEquals("An Amazing Product", product.getName());
    }

    @Test
    void getProductInfo_ProductPriceIsNotInDynamoDb_ExpectException() {
        Mockito.when(productPriceRepository.findById(Integer.valueOf(321))).thenReturn(Optional.empty());
        Mockito.when(redSkyService.getProductInfo(321)).thenReturn(happyRedSkyResponse);

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            productController.getProduct(321);
        });

        String expectedMessage = "ProductPriceNotFoundException";
        String actualMessage = responseStatusException.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
