package com.itschool.jpa.repositories;

import com.itschool.jpa.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
