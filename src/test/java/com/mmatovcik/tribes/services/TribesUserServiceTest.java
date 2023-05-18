package com.mmatovcik.tribes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.mmatovcik.tribes.exceptions.NotFoundException;
import com.mmatovcik.tribes.exceptions.NotUniqueException;
import com.mmatovcik.tribes.models.TribesUser;
import com.mmatovcik.tribes.repositories.TribesUserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TribesUserServiceTest {

  @Autowired TribesUserServiceImpl userService;
  @MockBean TribesUserRepository userRepository;
  @MockBean KingdomServiceImpl kingdomService;

  @Test
  public void register_successful() {  //FIXME
    // Given
    String username = "username";
    String password = "password";
    TribesUser userToRegister = new TribesUser(username, password);
    TribesUser expectedUser = new TribesUser("someId", username, password);
    when(userRepository.save(any())).thenReturn(expectedUser);
    // When
    TribesUser actualUser = userService.register(userToRegister, "kingdom");

    // Then
    assertEquals(expectedUser, actualUser);
  }

  @Test
  public void register_throwsNotUniqueException() {
    // Given
    TribesUser userToRegister = new TribesUser();
    userToRegister.setUsername("username");
    when(userRepository.findByUsername(any()))
        .thenReturn(Optional.of(new TribesUser()));

    // When
    Exception exception =
        assertThrows(NotUniqueException.class, () -> userService.register(userToRegister, "kingdom"));

    //Then
    assertTrue(exception.getMessage().contains("Username is already registered."));

  }

  @Test
  public void findUserByUsername_successful() {
    // Given
    String username = "username";
    TribesUser expectedUser = new TribesUser(username, "password");
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

    // When
    TribesUser actualUser = userService.findUserByUsername(username);

    // Then
    assertEquals(expectedUser, actualUser);
  }

  @Test
  public void findUserByUsername_throwsNotFoundException() {
    // Given

    //When
    Exception exception = assertThrows(NotFoundException.class, () -> userService.findUserByUsername("username"));

    //Then
    assertTrue(exception.getMessage().contains("Username does not exists"));
  }
}
