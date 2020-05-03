package com.kelvingoodman.myRetail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Product price not found")
public class ProductPriceNotFoundException extends Exception {
}
