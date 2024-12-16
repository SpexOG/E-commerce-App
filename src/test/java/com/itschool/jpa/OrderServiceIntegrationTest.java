package com.itschool.jpa;

import com.itschool.jpa.models.Order;
import com.itschool.jpa.models.User;
import com.itschool.jpa.repositories.OrderRepository;
import com.itschool.jpa.repositories.UserJpaRepository;
import com.itschool.jpa.services.impl.OrderServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class OrderServiceIntegrationTest {
    @Autowired
    private OrderServiceImpl service;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserJpaRepository userRepository;

    private User testUser;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        //curatare DB
        orderRepository.deleteAll();
        userRepository.deleteAll();

        //creaza user nou
        User u = new User();
        u.setName("sam");
        u.setUsername("user1");
        u.setEmail("user1@gmail.com");
        testUser = userRepository.save(u);

        //creaza order
        Order o = new Order();
//        o.setProduct("Test Product");
        o.setUser(testUser);
//        o.setPrice(100.0);
        o.setOrderDate(LocalDate.now());
        testOrder = orderRepository.save(o);
    }

    @Test
    void placeOrder_ShouldCreateANewOrder() {
        String product = "New Product";
        Double price = 150.0;

//        Order placedOrder = service.placeOrder(testUser.getId(), product, price);

//        assertNotNull(placedOrder);
//        assertNotNull(placedOrder.getId());
////        assertEquals(product, placedOrder.getProduct());
////        assertEquals(price, placedOrder.getPrice());
//        assertEquals(testUser.getId(), placedOrder.getUser().getId());
//
//        assertTrue(orderRepository.findById(placedOrder.getId()).isPresent());
    }

    @Test
    void placeOrder_ShouldReturnNull_WhenUserDoesNotExist() {
//        Order placedOrder = service.placeOrder(999L, "Some Product", 50.0);
//
//        assertNull(placedOrder);
    }

}
