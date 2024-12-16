package com.itschool.jpa.services.impl;

import com.itschool.jpa.enums.OrderStatus;
import com.itschool.jpa.models.Instrument;
import com.itschool.jpa.models.Order;
import com.itschool.jpa.models.OrderItem;
import com.itschool.jpa.models.User;
import com.itschool.jpa.repositories.InstrumentRepository;
import com.itschool.jpa.repositories.OrderRepository;
import com.itschool.jpa.repositories.UserJpaRepository;
import com.itschool.jpa.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserJpaRepository userRepository;
    @Autowired
    private InstrumentRepository instrumentRepository;

    /**
     * Places an order for a specific user
     */
    public Order placeOrder(Long userId, List<OrderItem> items) {
        if (items == null || items.isEmpty())
            throw new IllegalArgumentException("Order must contain at least one item!");


        Optional<User> userOpt = userRepository.findById(userId);
        Order order = new Order();
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            order.setUser(user);
            order.setStatus(OrderStatus.PENDING);
            order.setOrderDate(LocalDate.now());

            List<OrderItem> orderItems = items.stream()
                    .map(item -> {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setOrder(order);
                        orderItem.setQuantity(item.getQuantity());

                        if (item.getInstrument() == null) {
                            Instrument instrument = instrumentRepository.findById(item.getInstrument().getId())
                                    .orElseThrow(() -> new EntityNotFoundException("Instrument not found!"));
                            orderItem.setInstrument(instrument);
                        } else {
                            System.out.println("item.getInstrument() = " + item.getInstrument());
                            orderItem.setInstrument(item.getInstrument());
                        }

                        return orderItem;
                    }).toList();

            order.setItems(orderItems);
            order.calculateTotalAmount();
            items.forEach(OrderItem::updateInstrumentStock);
        } else {
            throw new EntityNotFoundException("User was not found!");
        }

        return orderRepository.save(order);
    }

    public List<Order> getOrdersWithPagination(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Order> orderPage = orderRepository.findByUserId(userId, pageRequest);
        return orderPage.getContent();
    }

    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order was not found!"));

        order.getItems().forEach(item -> {
            Instrument instrument = item.getInstrument();
            instrument.setStockQuantity(instrument.getStockQuantity() + item.getQuantity());
        });

        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}
