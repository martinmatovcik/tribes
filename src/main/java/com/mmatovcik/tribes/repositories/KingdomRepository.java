package com.mmatovcik.tribes.repositories;

import com.mmatovcik.tribes.models.Kingdom;
import com.mmatovcik.tribes.models.Location;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KingdomRepository extends MongoRepository<Kingdom, String> {

  Optional<Kingdom> findByLocation(Location location);
}
