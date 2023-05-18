package com.mmatovcik.tribes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.mmatovcik.tribes.models.Kingdom;
import com.mmatovcik.tribes.models.Location;
import com.mmatovcik.tribes.models.TribesUser;
import com.mmatovcik.tribes.repositories.KingdomRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class KingdomServiceTest {

  @Autowired KingdomServiceImpl kingdomService;
  @MockBean KingdomRepository kingdomRepository;

  @Test
  public void createKingdom_successful() {
    // Given
    TribesUser mockUser = new TribesUser();
    Location location = new Location(0,0);
    Kingdom expectedKingdom = new Kingdom(mockUser, "kingdom", location);
    
    doReturn(Optional.of(expectedKingdom)).when(kingdomRepository).findByLocation(location);
    doReturn(Optional.empty()).when(kingdomRepository).findByLocation(any());
    doReturn(expectedKingdom).when(kingdomRepository).save(any());

    // When
    Kingdom actualKingdom = kingdomService.createKingdom(mockUser, "kingdom");

    // Then
    assertEquals(expectedKingdom.getName(), actualKingdom.getName());

  }
}