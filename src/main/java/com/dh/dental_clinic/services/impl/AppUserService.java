package com.dh.dental_clinic.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dh.dental_clinic.dto.UserDTO;
import com.dh.dental_clinic.entity.AppUser;
import com.dh.dental_clinic.entity.AuthenticationReq;
import com.dh.dental_clinic.entity.TokenInfo;
import com.dh.dental_clinic.exceptions.TheEntityAlredyExistsException;
import com.dh.dental_clinic.repository.IUserRepository;
import com.dh.dental_clinic.utils.ConvertTo;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AppUserService implements UserDetailsService{

  
  private RestTemplate restTemplate = new RestTemplate();
  
  private final IUserRepository userRepository;

  @Autowired
  public AppUserService(IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    log.info(email);
    Optional<AppUser> user = userRepository.findByUsername(email);
    log.info("user: " + user.toString());
    return user.orElseThrow(() -> new UsernameNotFoundException(email));
  }
  
  public UserDTO save(AppUser user) throws TheEntityAlredyExistsException {
    Optional<AppUser> existingUser = userRepository.findByUsername(user.getUsername());
    if(existingUser.isPresent()) {
      throw new TheEntityAlredyExistsException(user);
    }
    UserDTO userDTO = ConvertTo.dto(userRepository.save(user), UserDTO.class);
    AuthenticationReq authenticationReq = new AuthenticationReq("admin", "admin");

    HttpEntity<AuthenticationReq> httpEntity = new HttpEntity<>(authenticationReq);

    ResponseEntity<TokenInfo> response = restTemplate.postForEntity("http://localhost:8080/authenticate", httpEntity, TokenInfo.class);

    TokenInfo tokenInfo = response.getBody();
    userDTO.setJwt(tokenInfo == null ? "" : tokenInfo.jwtToken());
    return userDTO;
  }

  // public void deleteByUsername(String username) {
  //   userRepository.deleteByUsername(username);
  // }
}
