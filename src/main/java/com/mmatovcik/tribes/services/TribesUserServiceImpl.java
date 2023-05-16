package com.mmatovcik.tribes.services;

import com.mmatovcik.tribes.exceptions.NotFoundException;
import com.mmatovcik.tribes.models.TribesUser;
import com.mmatovcik.tribes.repositories.TribesUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TribesUserServiceImpl implements TribesUserService {
  private final TribesUserRepository userRepository;

  @Override
  public void register(TribesUser userToRegister) {
    userRepository.save(userToRegister);
  }

  @Override
  public TribesUser loadUserByUsername(String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new NotFoundException("Username does not exists"));
  }
}
