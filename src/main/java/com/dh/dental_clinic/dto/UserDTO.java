package com.dh.dental_clinic.dto;

import com.dh.dental_clinic.entity.AppUserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
  private String name;
  private String username;
  private String email;
  private AppUserRole appUserRole;
  private String jwt;
}
