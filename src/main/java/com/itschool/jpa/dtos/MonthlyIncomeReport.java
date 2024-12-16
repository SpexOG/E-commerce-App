package com.itschool.jpa.dtos;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Month;

@Data
@ToString
public class MonthlyIncomeReport {
    private Month month;
    private int year;
    private BigDecimal totalIncome;
    private BigDecimal averageOrderValue;
    private int totalOrderCount;

}
