package com.mmatovcik.tribes.services;

import com.mmatovcik.tribes.exceptions.NotFoundException;
import com.mmatovcik.tribes.exceptions.NotUniqueException;
import com.mmatovcik.tribes.models.Role;
import com.mmatovcik.tribes.models.TribesUser;
import com.mmatovcik.tribes.repositories.TribesUserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TribesUserServiceImpl implements TribesUserService {
  private final TribesUserRepository userRepository;
  private final KingdomService kingdomService;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  @Override
  public TribesUser register(TribesUser userToRegister, String kingdomName) {
    if (isUserDatabaseEmpty()) {
      addAdminToDatabase();
    }

    String usernameToRegister = userToRegister.getUsername();

    if (usernameExists(usernameToRegister)) {
      throw new NotUniqueException("Username is already registered.");
    }

    TribesUser registeredUser =
        new TribesUser(
            usernameToRegister, passwordEncoder.encode(userToRegister.getPassword()), Role.USER);

    userRepository.save(registeredUser);
    kingdomService.createKingdom(registeredUser, kingdomName);

    return registeredUser;
  }

  private void addAdminToDatabase() {
    userRepository.save(new TribesUser("admin", passwordEncoder.encode("admin"), Role.ADMIN));
  }

  private Boolean isUserDatabaseEmpty() {
    return userRepository.findAll().isEmpty();
  }

  private Boolean usernameExists(String username) {
    return loadOptionalOfUserByUsername(username).isPresent();
  }

  @Override
  public TribesUser findUserByUsername(String username) {
    return loadOptionalOfUserByUsername(username)
        .orElseThrow(() -> new NotFoundException("Username does not exists"));
  }

  @Override
  public String login(String username, String password) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
    } catch (AuthenticationException e) {
      throw new BadCredentialsException("Username or password is not valid.");
    }
    TribesUser user = findUserByUsername(username);

    return jwtService.generateToken(user);
  }

  private Optional<TribesUser> loadOptionalOfUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
