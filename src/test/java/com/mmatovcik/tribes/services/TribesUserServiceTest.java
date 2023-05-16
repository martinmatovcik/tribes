package com.mmatovcik.tribes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.mmatovcik.tribes.exceptions.NotFoundException;
import com.mmatovcik.tribes.exceptions.NotUniqueException;
import com.mmatovcik.tribes.models.Kingdom;
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

  @Test
  public void register_successful() {
    // Given
    String username = "username";
    String password = "password";
    Kingdom kingdom = new Kingdom();
    TribesUser userToRegister = new TribesUser(username, password, kingdom);
    TribesUser expectedUser = new TribesUser("1", username, password, kingdom);

    // When
    TribesUser actualUser = userService.register(userToRegister);

    // Then
    assertEquals(expectedUser, actualUser);
  }

  @Test
  public void register_throwsNotUniqueException() {
    // Given
    TribesUser userToRegister = new TribesUser();
    userToRegister.setUsername("username");
    when(userService.loadUserFromDatabaseByUsername(any()))
        .thenReturn(Optional.of(new TribesUser()));

    // When
    Exception exception =
        assertThrows(NotUniqueException.class, () -> userService.register(userToRegister));

    //Then
    assertTrue(exception.getMessage().contains("Username is registered already."));

  }

  @Test
  public void loadUserByUsername_successful() {
    // Given
    String username = "username";
    TribesUser expectedUser = new TribesUser(username, "password", new Kingdom());
    when(userService.loadUserFromDatabaseByUsername(username)).thenReturn(Optional.of(expectedUser));

    // When
    TribesUser actualUser = userService.loadUserByUsername(username);

    // Then
    assertEquals(expectedUser, actualUser);
  }

  @Test
  public void loadUserByUsername_throwsNotFoundException() {
    // Given

    //When
    Exception exception = assertThrows(NotFoundException.class, () -> userService.loadUserByUsername("username"));

    //Then
    assertTrue(exception.getMessage().contains("Username does not exists"));
  }

  @Test
  public void loadUserFromDatabase_successful() {
    //Given
    String username = "username";
    TribesUser expectedUser = new TribesUser();
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

    //When
    Optional<TribesUser> actualUserOptional = userService.loadUserFromDatabaseByUsername(username);

    //Then
    assertEquals(Optional.of(expectedUser), actualUserOptional);
  }
}
