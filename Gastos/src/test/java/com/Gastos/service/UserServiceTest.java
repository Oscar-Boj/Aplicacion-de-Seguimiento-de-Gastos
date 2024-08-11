package com.Gastos.service;

import com.Gastos.model.User;
import com.Gastos.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("plainPassword");

        // Mocking the password encoder
        when(bCryptPasswordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

        // Mocking the repository save method
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User savedUser = userService.saveUser(user);

        // Verify that the password was encoded before saving
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("testUser", savedUser.getUsername());

        verify(bCryptPasswordEncoder, times(1)).encode("plainPassword");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");

        // Mocking the repository findByUsername method
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findByUsername("testUser");

        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
        assertEquals("encodedPassword", foundUser.get().getPassword());

        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void testFindByUsernameNotFound() {
        // Mocking the repository findByUsername method to return empty
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.findByUsername("nonExistentUser");

        assertTrue(foundUser.isEmpty());
        verify(userRepository, times(1)).findByUsername("nonExistentUser");
    }
}
