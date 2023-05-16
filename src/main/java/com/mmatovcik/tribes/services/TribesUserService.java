package com.mmatovcik.tribes.services;

import com.mmatovcik.tribes.models.TribesUser;
import java.util.Optional;

public interface TribesUserService {
  TribesUser register(TribesUser userToRegister);

  TribesUser loadUserByUsername(String username);
  Optional<TribesUser> loadUserFromDatabaseByUsername(String username);
}
