package com.mmatovcik.tribes.services;

import com.mmatovcik.tribes.models.TribesUser;

public interface TribesUserService {
  TribesUser register(TribesUser userToRegister, String kingdomName);

  TribesUser findUserByUsername(String username);

  String login(String username, String password);
}
