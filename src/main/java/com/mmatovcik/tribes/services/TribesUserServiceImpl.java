package com.mmatovcik.tribes.services;

import com.mmatovcik.tribes.exceptions.NotFoundException;
import com.mmatovcik.tribes.exceptions.NotUniqueException;
import com.mmatovcik.tribes.models.TribesUser;
import com.mmatovcik.tribes.repositories.TribesUserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TribesUserServiceImpl implements TribesUserService {
  private final TribesUserRepository userRepository;
  private final KingdomService kingdomService;

  @Override
  public TribesUser register(TribesUser userToRegister, String kingdomName) {
    String usernameToRegister = userToRegister.getUsername();

    if (usernameExists(usernameToRegister)) {
      throw new NotUniqueException("Username is already registered.");
    }

    TribesUser registeredUser =
        userRepository.save(
            new TribesUser(usernameToRegister, userToRegister.getPassword()));
    kingdomService.createKingdom(registeredUser, kingdomName);
    return registeredUser;
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
    return "JWT token";
  }

  private Optional<TribesUser> loadOptionalOfUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
