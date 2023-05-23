package com.mmatovcik.tribes.dtos;

import com.mmatovcik.tribes.models.Location;
import com.mmatovcik.tribes.models.Resource;
import com.mmatovcik.tribes.models.TribesUser;
import com.mmatovcik.tribes.models.Troop;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KingdomDto {
  private String id;
  private TribesUser user;
  private String name;
  private List<Troop> troops = new LinkedList<>();
  private List<Resource> resources = new LinkedList<>();
  private Location location;
}
