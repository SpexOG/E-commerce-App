package com.itschool.jpa.dtos;

import com.itschool.jpa.enums.OrderStatus;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@ToString
public class OrderWeeklyReport {
    private int totalOrders;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalRevenue;
    private Map<OrderStatus, Long> orderStatus;
}
