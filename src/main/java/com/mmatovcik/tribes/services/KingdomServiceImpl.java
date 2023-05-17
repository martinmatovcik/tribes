package com.mmatovcik.tribes.services;

import com.mmatovcik.tribes.models.Kingdom;
import com.mmatovcik.tribes.models.TribesUser;
import com.mmatovcik.tribes.repositories.KingdomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KingdomServiceImpl implements KingdomService {
  private final KingdomRepository kingdomRepository;

  @Override
  public Kingdom createNewKingdom(TribesUser user) {
    Kingdom newKingdom = new Kingdom(user);
    return kingdomRepository.save(newKingdom);
  }
}
