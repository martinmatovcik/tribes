package com.mmatovcik.tribes.services;

import com.mmatovcik.tribes.models.Kingdom;
import com.mmatovcik.tribes.models.TribesUser;
import java.util.List;

public interface KingdomService {
  Kingdom createKingdom(TribesUser user, String kingdomName);
  List<Kingdom> findAllKingdoms();
}
