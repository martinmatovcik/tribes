package com.mmatovcik.tribes.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TribesUser {
  @Id private String id;

  @Indexed(unique = true)
  private String username;

  private String password;
  private Kingdom kingdom;

  public TribesUser(String username, String password, Kingdom kingdom) {
    this.username = username;
    this.password = password;
    this.kingdom = kingdom;
  }
}
