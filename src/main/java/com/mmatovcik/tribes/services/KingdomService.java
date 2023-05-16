package com.mmatovcik.tribes.services;

import com.mmatovcik.tribes.models.Kingdom;
import com.mmatovcik.tribes.models.TribesUser;

public interface KingdomService {
  Kingdom createNewKingdom(TribesUser user);
}
