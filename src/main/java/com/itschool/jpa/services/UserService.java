package com.itschool.jpa.services;

import com.itschool.jpa.dtos.CreateUserDto;
import com.itschool.jpa.models.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Iterable<User> saveAll(List<User> users);

    Iterable<User> findAllUsers();

    void deleteUser(Long id);

    Optional<User> findUserByUsername(String username);

    User addUser(User user);

    User createUserFromDto(CreateUserDto userDto);
}
