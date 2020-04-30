package com.kelvingoodman.myRetail.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Price {
    private BigDecimal value;
    private String currency_code;

    public Price withValue(BigDecimal value) {
        setValue(value);
        return this;
    }

    public Price withCurrency_code(String currency_code) {
        setCurrency_code(currency_code);
        return this;
    }
}
