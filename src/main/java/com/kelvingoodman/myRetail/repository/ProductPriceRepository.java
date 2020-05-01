package com.kelvingoodman.myRetail.repository;

import com.kelvingoodman.myRetail.dao.ProductPrice;

public interface ProductPriceRepository {
    public ProductPrice getPrice(int id);

    public void savePrice();
}
