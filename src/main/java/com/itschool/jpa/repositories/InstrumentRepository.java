package com.itschool.jpa.repositories;

import com.itschool.jpa.enums.InstrumentCategory;
import com.itschool.jpa.models.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
    List<Instrument> findByCategory(InstrumentCategory category);

    List<Instrument> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Instrument> findByStockQuantityGreaterThan(int q);
}
