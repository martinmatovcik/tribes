package com.mmatovcik.tribes.dtos;

import com.mmatovcik.tribes.models.Location;
import com.mmatovcik.tribes.models.Resource;
import com.mmatovcik.tribes.models.TribesUser;
import com.mmatovcik.tribes.models.Troop;
import java.util.List;

public record KingdomDto(
    String id,
    TribesUser user,
    String name,
    List<Troop> troops,
    List<Resource> resources,
    Location location) {}
