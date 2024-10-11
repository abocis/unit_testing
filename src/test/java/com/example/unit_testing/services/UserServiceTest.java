package com.example.unit_testing.services;

import com.example.unit_testing.models.User;
import com.example.unit_testing.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    //mock
    @Mock
    private UserRepository userRepository;

    //injecera mocksen
    @InjectMocks
    private UserService userService;

    //initiera alla mokcs
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    //test create user
    @Test
    public void testCreateUser_success() {

        //Arrange
        //  skapa sample user
        User user = new User();
        user.setFirstName("james ");
        user.setLastName("doe");
        user.setEmail("james@doe.com");

        User savedUser = new User();
        savedUser.setId("1");
        savedUser.setFirstName(user.getFirstName());
        savedUser.setLastName(user.getLastName());
        savedUser.setEmail(user.getEmail());


        when(userRepository.save(user)).thenReturn( savedUser );

        //Act
        User result = userService.createUser(user);

        //Assert
        //verifiera att user sparades och har ett korrekt id

        assertNotNull(result.getId(), "saved user sould have an id ");
        assertEquals("james ", result.getFirstName(), "firsname should match");
        assertEquals("doe ", result.getLastName(), "lastname should match");
        assertEquals("james@doe.com", result.getEmail(), "email should match");

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUserById_success() {
        String userId = "1";
        User user = new User();
        user.setId(userId);
        user.setFirstName("abou ");
        user.setLastName("cisse");
        user.setEmail("james@doe.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);
    }





    @Test
    public void testgetUserById_userNotFound() {

        //arrange
        String userId = "not esistent_id";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(userId);
            }, "expeted if but dint");
    }

}


