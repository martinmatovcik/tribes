package com.mmatovcik.tribes.models;

import com.mmatovcik.tribes.dtos.TribesUserDto;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TribesUser implements UserDetails {
  @Id private String id;

  @Indexed(unique = true)
  private String username;

  private String password;
  
  private Role role = Role.USER;

  public TribesUser(String username, String password) {
    this.username = username;
    this.password = password;
  }
  public TribesUser(String id,String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }
  public TribesUser(String username, String password, Role role){
    this.username = username;
    this.password = password;
    this.role = role;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(this.role.name()));
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public TribesUserDto toDto() {
    return new TribesUserDto(username, role);
  }
}
