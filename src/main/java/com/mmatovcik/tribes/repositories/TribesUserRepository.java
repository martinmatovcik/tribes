package com.mmatovcik.tribes.repositories;

import com.mmatovcik.tribes.models.TribesUser;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TribesUserRepository extends MongoRepository<TribesUser, String> {
  Optional<TribesUser> findByUsername(String username);
}
