package com.dh.dental_clinic.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dh.dental_clinic.entity.AppUser;
import com.dh.dental_clinic.repository.IUserRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AppUserService implements UserDetailsService{

  
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
  
  // public UserDetails save(AppUser user) {
  //   Optional<AppUser> existingUser = userRepository.findByUsername(user.getUsername());
  //   return userRepository.save(user);
  // }

  // public void deleteByUsername(String username) {
  //   userRepository.deleteByUsername(username);
  // }
}
