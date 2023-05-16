package com.mmatovcik.tribes.services;

import com.mmatovcik.tribes.models.TribesUser;

public interface TribesUserService {
  void register(TribesUser userToRegister);

  TribesUser loadUserByUsername(String username);
}
