package com.dh.dental_clinic.entity;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationReq {
 private UUID id;
 private String user;
 private String password;
 
public AuthenticationReq(String user, String password) {
  this.user = user;
  this.password = password;
}
}
