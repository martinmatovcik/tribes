package com.mmatovcik.tribes.services;

import com.mmatovcik.tribes.exceptions.NotUniqueException;
import com.mmatovcik.tribes.models.Role;
import com.mmatovcik.tribes.models.TribesUser;
import com.mmatovcik.tribes.repositories.TribesUserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TribesUserService {
  private final TribesUserRepository userRepository;
  private final KingdomService kingdomService;

  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

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

  private Boolean isUserDatabaseEmpty() {
    return userRepository.findAll().isEmpty();
  }

  private void addAdminToDatabase() {
    userRepository.save(new TribesUser("admin", passwordEncoder.encode("admin"), Role.ADMIN));
  }

  private Boolean usernameExists(String username) {
    return loadOptionalOfUserByUsername(username).isPresent();
  }

  private Optional<TribesUser> loadOptionalOfUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public String login(String username, String password) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
    } catch (AuthenticationException e) {
      throw new BadCredentialsException("Username or password is not valid.");
    }

    return jwtService.generateToken(findUserByUsername(username));
  }

  private TribesUser findUserByUsername(String username) {
    return (TribesUser) userDetailsService.loadUserByUsername(username);
  }
}
