package com.itschool.jpa.controllers;

import com.itschool.jpa.dtos.CreateUserDto;
import com.itschool.jpa.exceptions.UserServiceException;
import com.itschool.jpa.models.User;
import com.itschool.jpa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService userService) {
        this.service = userService;
    }

    @PostMapping("/all")
    public ResponseEntity<Iterable<User>> createUsers(@RequestBody List<User> users) {
        Iterable<User> createdUsers = service.saveAll(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsers);
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> users = service.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) {
        Optional<User> user = service.findUserByUsername(username);

        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User savedUser = service.addUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/create-from-dto")
    public ResponseEntity<User> createUserFromDto(@RequestBody CreateUserDto userDto) {
        User user = new User();
        try {
            user = service.createUserFromDto(userDto);
        } catch (UserServiceException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getErrorCode());
        }
        return ResponseEntity.ok(user);
    }


}
