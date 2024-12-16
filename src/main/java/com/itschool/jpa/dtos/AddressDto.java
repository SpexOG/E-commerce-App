package com.itschool.jpa.dtos;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class AddressDto implements Serializable {

    private String city;
    private String street;
    private String country;
    private Integer numberOfStreet;
}
