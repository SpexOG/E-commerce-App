package com.itschool.jpa.dtos;

import com.itschool.jpa.models.Order;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@ToString
public class CreateUserDto implements Serializable {
    private String name;
    private Integer age;
    private LocalDate registrationDate;
    private String email;
    private String username;
    private boolean active;
    private AddressDto address;
    private List<Order> orders;
}
