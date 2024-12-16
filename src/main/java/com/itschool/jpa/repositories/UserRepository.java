package com.itschool.jpa.repositories;

import com.itschool.jpa.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
