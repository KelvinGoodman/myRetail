package com.kelvingoodman.myRetail.repository;

import com.kelvingoodman.myRetail.dao.ProductPrice;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface ProductPriceRepository extends CrudRepository<ProductPrice, Integer> {
    Optional<ProductPrice> findById(Integer id);
}
