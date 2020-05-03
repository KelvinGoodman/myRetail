package com.kelvingoodman.myRetail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Red Sky product not found")
public class RedSkyProductNotFoundException extends Exception {
}