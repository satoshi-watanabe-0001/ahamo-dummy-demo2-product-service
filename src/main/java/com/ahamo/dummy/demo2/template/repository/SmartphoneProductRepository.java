package com.ahamo.dummy.demo2.template.repository;

import com.ahamo.dummy.demo2.template.domain.entity.SmartphoneProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SmartphoneProductRepository extends JpaRepository<SmartphoneProduct, String> {

    @Query("SELECT sp FROM SmartphoneProduct sp WHERE (:brand IS NULL OR sp.brand = :brand)")
    Page<SmartphoneProduct> findByBrandWithPagination(@Param("brand") String brand, Pageable pageable);

    @Query("SELECT DISTINCT sp FROM SmartphoneProduct sp " +
           "LEFT JOIN FETCH sp.colorOptions " +
           "WHERE sp.id = :id")
    SmartphoneProduct findByIdWithColorOptions(@Param("id") String id);

    @Query("SELECT DISTINCT sp FROM SmartphoneProduct sp " +
           "LEFT JOIN FETCH sp.features " +
           "WHERE sp.id = :id")
    SmartphoneProduct findByIdWithFeatures(@Param("id") String id);

    @Query("SELECT DISTINCT sp FROM SmartphoneProduct sp " +
           "LEFT JOIN FETCH sp.specifications " +
           "WHERE sp.id = :id")
    SmartphoneProduct findByIdWithSpecifications(@Param("id") String id);
}
