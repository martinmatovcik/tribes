package com.mmatovcik.tribes.controllers;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmatovcik.tribes.exceptions.NoContentException;
import com.mmatovcik.tribes.models.Kingdom;
import com.mmatovcik.tribes.models.Location;
import com.mmatovcik.tribes.models.Role;
import com.mmatovcik.tribes.models.TribesUser;
import com.mmatovcik.tribes.repositories.KingdomRepository;
import com.mmatovcik.tribes.repositories.TribesUserRepository;
import com.mmatovcik.tribes.services.JwtService;
import com.mmatovcik.tribes.services.KingdomService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class KingdomControllerTest {
  @Autowired KingdomController kingdomController;
  @Autowired KingdomService kingdomService;
  @Autowired UserDetailsService userDetailsService;
  @Autowired WebApplicationContext context;

  @MockBean TribesUserRepository userRepository;
  @MockBean JwtService jwtService;
  @MockBean KingdomRepository kingdomRepository;

  MockMvc mockMvc;
  ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  public void getAllKingdoms_successful() throws Exception {
    // Given
    List<Kingdom> expectedKingdoms =
        List.of(
            new Kingdom(new TribesUser(), "kingdom01", new Location(0, 0)),
            new Kingdom(new TribesUser(), "kingdom02", new Location(1, 1)),
            new Kingdom(new TribesUser(), "kingdom03", new Location(3, 3)));
    when(kingdomRepository.findAll()).thenReturn(expectedKingdoms);
    useUserWithRole(Role.USER);

    // When
    mockMvc
        .perform(
            get("/api/kingdoms")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))

        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(3)))
        .andExpect(jsonPath("$[0].name", is("kingdom01")))
        .andExpect(jsonPath("$[1].name", is("kingdom02")))
        .andExpect(jsonPath("$[2].name", is("kingdom03")));
  }

  @Test
  public void getAllKingdoms_throwsNoContentException() throws Exception {
    // Given
    List<Kingdom> expectedKingdoms = new ArrayList<>();
    when(kingdomRepository.findAll()).thenReturn(expectedKingdoms);
    useUserWithRole(Role.USER);

    // When
    mockMvc
        .perform(
            get("/api/kingdoms")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))

        // Then
        .andExpect(status().isNoContent())
        .andExpect(
            result ->
                assertTrue(
                    result.getResolvedException() instanceof NoContentException
                        && (result.getResolvedException().getMessage().contains("No content."))));
  }

  private void useUserWithRole(Role role) {
    TribesUser user = new TribesUser();
    user.setUsername(role.name());
    user.setRole(role);

    when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
    when(jwtService.extractUsername(any())).thenReturn(user.getUsername());
    when(jwtService.isTokenValidForUsername(any(), any())).thenReturn(true);
  }
}
