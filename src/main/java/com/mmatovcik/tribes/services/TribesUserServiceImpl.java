package com.mmatovcik.tribes.services;

import com.mmatovcik.tribes.exceptions.NotFoundException;
import com.mmatovcik.tribes.exceptions.NotUniqueException;
import com.mmatovcik.tribes.models.TribesUser;
import com.mmatovcik.tribes.repositories.TribesUserRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TribesUserServiceImpl implements TribesUserService {
  private final TribesUserRepository userRepository;

  @Override
  public TribesUser register(TribesUser userToRegister) {
    if (loadUserFromDatabaseByUsername(userToRegister.getUsername()).isPresent()) {
      throw new NotUniqueException("Username is registered already.");
    }
    TribesUser registeredUser = userRepository.insert(userToRegister);
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
