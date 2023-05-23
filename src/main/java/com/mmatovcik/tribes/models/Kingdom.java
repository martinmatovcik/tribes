package com.mmatovcik.tribes.models;

import com.mmatovcik.tribes.dtos.KingdomDto;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "kingdoms")
@NoArgsConstructor
public class Kingdom {
  @Id private String id;
  private TribesUser user;
  private String name;
  private List<Troop> troops = new LinkedList<>();
  private List<Resource> resources = new LinkedList<>();
  private Location location;

  public Kingdom(TribesUser user, String name, Location location) {
    this.user = user;
    this.name = name;
    this.location = location;
  }

  public KingdomDto toDto() {
    return new KingdomDto(
        this.id, this.user, this.name, this.troops, this.resources, this.location);
  }
}
