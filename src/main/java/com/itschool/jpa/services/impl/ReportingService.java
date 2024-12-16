package com.itschool.jpa.services.impl;

import com.itschool.jpa.dtos.MonthlyIncomeReport;
import com.itschool.jpa.dtos.OrderWeeklyReport;
import com.itschool.jpa.models.Order;
import com.itschool.jpa.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportingService {
    @Autowired
    private OrderRepository orderRepository;

    @Scheduled(cron = "0 0 9 ? * MON")
    public void generateWeeklyOrderReport() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(7);

        List<Order> weeklyOrders = orderRepository.findAllByOrderDateBetween(startDate, endDate);

        OrderWeeklyReport orderWeeklyReport = new OrderWeeklyReport();
        orderWeeklyReport.setStartDate(startDate);
        orderWeeklyReport.setEndDate(endDate);
        orderWeeklyReport.setTotalOrders(weeklyOrders.size());
        //@formatter:off
        orderWeeklyReport.setTotalRevenue(calculateTotalIncome(weeklyOrders));

        orderWeeklyReport.setOrderStatus(weeklyOrders.stream()
                                                     .collect(Collectors.groupingBy(Order::getStatus, Collectors.counting())));//@formatter:on

        System.out.println(orderWeeklyReport);
    }

    @Scheduled(cron = "* * 9 1 * *")
    public void generateMonthlyIncomeReport() {
        LocalDate now = LocalDate.now().minusMonths(1);
        LocalDate firstDayOfMonth = now.withDayOfMonth(1);
        LocalDate lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        List<Order> orderList = orderRepository.findAllByOrderDateBetween(firstDayOfMonth, lastDayOfMonth);

        MonthlyIncomeReport report = new MonthlyIncomeReport();
        report.setYear(now.getYear());
        report.setMonth(now.getMonth());
        report.setTotalIncome(calculateTotalIncome(orderList));
        report.setAverageOrderValue(calculateTotalIncome(orderList).divide(BigDecimal.valueOf(orderList.size()), 2, RoundingMode.HALF_UP));
        report.setTotalOrderCount(orderList.size());
        System.out.println("Monthly report = " + report);
    }

    private static BigDecimal calculateTotalIncome(List<Order> orderList) {
        return orderList.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
