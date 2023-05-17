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
  public TribesUser register(TribesUser userToRegister) {
    if (loadUserFromDatabaseByUsername(userToRegister.getUsername()).isPresent()) {
      throw new NotUniqueException("Username is already registered.");
    }

    TribesUser registeredUser =
        userRepository.save(
            new TribesUser(userToRegister.getUsername(), userToRegister.getPassword()));
    kingdomService.createNewKingdom(registeredUser);
    return registeredUser;
  }

  @Override
  public TribesUser loadUserByUsername(String username) {
    return loadUserFromDatabaseByUsername(username)
        .orElseThrow(() -> new NotFoundException("Username does not exists"));
  }

  @Override
  public Optional<TribesUser> loadUserFromDatabaseByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
