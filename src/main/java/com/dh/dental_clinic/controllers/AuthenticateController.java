package com.dh.dental_clinic.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dh.dental_clinic.entity.AuthenticationReq;
import com.dh.dental_clinic.entity.TokenInfo;
import com.dh.dental_clinic.services.impl.AppUserService;
import com.dh.dental_clinic.services.impl.JwtUtilService;

@RestController
@RequestMapping("/authenticate")
public class AuthenticateController {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private AppUserService appUserService;

  @Autowired
  private JwtUtilService jwtUtilService;

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @PostMapping()
  public ResponseEntity<TokenInfo> authenticate(@RequestBody AuthenticationReq authenticationReq) {
    logger.info("Autenticando al usuario {}", authenticationReq.getUser());

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authenticationReq.getUser(),
            authenticationReq.getPassword()));

    final UserDetails userDetails = appUserService.loadUserByUsername(
        authenticationReq.getUser());

    logger.info("Usuario autenticado {}", userDetails);
    final String jwt = jwtUtilService.generateToken(userDetails);

    logger.info("Token generado {}", jwt);

    return ResponseEntity.ok(new TokenInfo(jwt));
  }
}
