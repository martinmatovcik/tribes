package com.mmatovcik.tribes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.mmatovcik.tribes.dtos.LoginRequestDto;
import com.mmatovcik.tribes.exceptions.NotUniqueException;
import com.mmatovcik.tribes.models.TribesUser;
import com.mmatovcik.tribes.repositories.KingdomRepository;
import com.mmatovcik.tribes.repositories.TribesUserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class TribesUserServiceTest { //  FIXME

  @Autowired
  TribesUserService userService;
  @Autowired
  JwtService jwtService;

  @MockBean TribesUserRepository userRepository;
  @MockBean UserDetailsService userDetailsService;
  @MockBean AuthenticationManager authenticationManager;
  @MockBean PasswordEncoder passwordEncoder;
  @MockBean KingdomService kingdomService;
  @MockBean KingdomRepository kingdomRepository;

  @Test
  public void register_successful() {
    // Given
    String username = "username";
    String password = "password";

    // When
    TribesUser actualUser = userService.register(new TribesUser(username, password), "kingdom");

    // Then
    assertEquals(username, actualUser.getUsername());
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
  public void login_successful() {
    // Given
    var username = "username";
    LoginRequestDto requestDto = new LoginRequestDto(username, "password");

    TribesUser user = requestDto.toUser();

    when(userDetailsService.loadUserByUsername(username)).thenReturn(user);

    // When
    String actualToken = userService.login(requestDto.username(), requestDto.password());

    // Then
    assertEquals(username, jwtService.extractUsername(actualToken));

  }

  //  @Test
  //  public void findUserByUsername_successful() {
  //    // Given
  //    String username = "username";
  //    TribesUserInterface expectedUser = new TribesUserInterface(username, "password");
  //    when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));
  //
  //    // When
  //    TribesUserInterface actualUser = userService.findUserByUsername(username);
  //
  //    // Then
  //    assertEquals(expectedUser, actualUser);
  //  }
  //
  //  @Test
  //  public void findUserByUsername_throwsNotFoundException() {
  //    // Given
  //
  //    //When
  //    Exception exception = assertThrows(NotFoundException.class, () ->
  // userService.findUserByUsername("username"));
  //
  //    //Then
  //    assertTrue(exception.getMessage().contains("Username does not exists"));
  //  }
}
