package com.example.autoservice.service;

import com.example.autoservice.model.CustomerUser;
import com.example.autoservice.model.User;
import com.example.autoservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        userService = new UserService(userRepository);
    }

    @Test
    void register_shouldSaveUser() {
        User user = new CustomerUser(null, "Тест", "test@mail.ru");
        User saved = userService.register(user);
        assertNotNull(saved.getId());
        assertEquals("test@mail.ru", saved.getEmail());
    }

    @Test
    void register_shouldThrowWhenEmailEmpty() {
        User user = new CustomerUser(null, "Тест", "");
        assertThrows(IllegalArgumentException.class, () -> userService.register(user));
    }

    @Test
    void register_shouldThrowWhenEmailDuplicate() {
        userService.register(new CustomerUser(null, "A", "dup@mail.ru"));
        assertThrows(IllegalStateException.class,
                () -> userService.register(new CustomerUser(null, "B", "dup@mail.ru")));
    }

    @Test
    void findById_shouldReturnUser() {
        User saved = userService.register(new CustomerUser(null, "A", "a@mail.ru"));
        Optional<User> found = userService.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
    }

    @Test
    void delete_shouldRemoveUser() {
        User saved = userService.register(new CustomerUser(null, "A", "a@mail.ru"));
        assertTrue(userService.delete(saved.getId()));
        assertTrue(userService.findById(saved.getId()).isEmpty());
    }
}