package com.itschool.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itschool.jpa.dtos.CreateUserDto;
import com.itschool.jpa.exceptions.EmailSendingException;
import com.itschool.jpa.exceptions.UserServiceException;
import com.itschool.jpa.models.User;
import com.itschool.jpa.repositories.UserJpaRepository;
import com.itschool.jpa.services.impl.EmailService;
import com.itschool.jpa.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserJpaRepository jpaRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private EmailService emailService;

    private User testUser;
    private CreateUserDto testUserDto;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Sam");
        testUser.setUsername("user1");
        testUser.setEmail("user1@gmail.com");

        testUserDto = new CreateUserDto();
    }

    @Test
    void saveAll_ShouldReturnSavedUsers() {
        List<User> users = Arrays.asList(testUser);
        when(jpaRepository.saveAll(users)).thenReturn(users);

        Iterable<User> savedUsers = userService.saveAll(users);

        assertNotNull(savedUsers);
        verify(jpaRepository).saveAll(users);
    }

    @Test
    void findAllUsers_ShouldReturnAllUsers() {
        List<User> users = Arrays.asList(testUser);
        when(jpaRepository.findAll()).thenReturn(users);

        Iterable<User> foundUsers = userService.findAllUsers();

        assertNotNull(foundUsers);
        verify(jpaRepository).findAll();
    }

    @Test
    void deleteUser_ShouldDeleteUserById() {
        Long userId = 1L;
        doNothing().when(jpaRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(jpaRepository).deleteById(userId);
    }

    @Test
    void createUserFromDto_ShouldCreateAndSendEmail() throws EmailSendingException {
        when(mapper.convertValue(testUserDto, User.class)).thenReturn(testUser);
        when(jpaRepository.save(testUser)).thenReturn(testUser);
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());

        User savedUser = userService.createUserFromDto(testUserDto);

        assertNotNull(savedUser);
        verify(mapper).convertValue(testUserDto, User.class);
        verify(jpaRepository).save(any(User.class));
        verify(emailService).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void createUserFromDto_ShouldThrowException_WhenEmailFailed() throws EmailSendingException {
        when(mapper.convertValue(testUserDto, User.class)).thenReturn(testUser);
        when(jpaRepository.save(testUser)).thenReturn(testUser);
        doThrow(new EmailSendingException("Failed to send email!")).when(emailService).sendEmail(anyString(), anyString(), anyString());

        assertThrows(UserServiceException.class, () -> userService.createUserFromDto(testUserDto));

        verify(mapper).convertValue(testUserDto,User.class);
        verify(jpaRepository).save(any(User.class));
        verify(emailService).sendEmail(anyString(),anyString(),anyString());

    }
}
