package com.kelvingoodman.myRetail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unable to handle request at this time")
public class GenericException extends Exception {
}
