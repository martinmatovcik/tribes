package com.mmatovcik.tribes.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class TribesUser {
  @Id private String id;

  @Indexed(unique = true)
  private String username;

  private String password;
  private Kingdom kingdom;
}
