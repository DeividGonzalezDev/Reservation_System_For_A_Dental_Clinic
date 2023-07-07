package com.dh.dental_clinic.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.w3c.dom.UserDataHandler;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@ToString
@Entity
@NoArgsConstructor
@Log4j2
public class AppUser implements UserDetails {
 @Id
 @GeneratedValue
 private UUID id; 

 @Getter
 @Setter
 private String name;

 @Setter
 private String username;

 @Getter
 @Setter
 private String email;

 @Setter
 private String password;

 @Getter
 @Setter
 @Enumerated(EnumType.STRING)
 private AppUserRole appUserRole;


public AppUser(String name, String username, String email, String password,
   AppUserRole appUserRole) {
  this.name = name;
  this.username = username;
  this.email = email;
  this.password = password;
  this.appUserRole = appUserRole;
}

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
  SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
  log.info(authority.toString());
  return Collections.singleton(authority);
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

@Override
public String getPassword() {
  return password;
}

@Override
public String getUsername() {
  return username;
}
}
