package com.mmatovcik.tribes.services;

import com.mmatovcik.tribes.models.Kingdom;
import com.mmatovcik.tribes.models.Location;
import com.mmatovcik.tribes.models.TribesUser;
import com.mmatovcik.tribes.repositories.KingdomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KingdomServiceImpl implements KingdomService {
  private static final Integer GAME_BOARD_SIZE = 200;
  private final KingdomRepository kingdomRepository;

  private static int generateCoordinate() {
    return (int) (Math.random() * GAME_BOARD_SIZE);
  }

  @Override
  public Kingdom createKingdom(TribesUser user, String kingdomName) {
    Kingdom newKingdom = new Kingdom(user, kingdomName, generateLocation());
    return kingdomRepository.save(newKingdom);
  }

  @Override
  public List<Kingdom> findAllKingdoms() {
    return kingdomRepository.findAll();
  }

  private Location generateLocation() {
    Location location;
    do {
      location =
          new Location(
              generateCoordinate(), generateCoordinate());
    } while (isLocationOccupied(location));
    return location;
  }

  private Boolean isLocationOccupied(Location location) {
    return kingdomRepository.findByLocation(location).isPresent();
  }
}
