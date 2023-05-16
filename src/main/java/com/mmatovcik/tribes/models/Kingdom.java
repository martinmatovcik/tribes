package com.mmatovcik.tribes.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "kingdoms")
public class Kingdom {
  @Id private String id;
}
