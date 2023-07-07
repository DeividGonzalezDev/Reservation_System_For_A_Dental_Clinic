package com.dh.dental_clinic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dh.dental_clinic.dto.UserDTO;
import com.dh.dental_clinic.entity.AppUser;
import com.dh.dental_clinic.entity.AppUserRole;
import com.dh.dental_clinic.entity.Booking;
import com.dh.dental_clinic.exceptions.TheEntityAlredyExistsException;
import com.dh.dental_clinic.exceptions.TheNecessaryEntitiesForTheOperationDoNotExistException;
import com.dh.dental_clinic.services.impl.AppUserService;
import com.dh.dental_clinic.utils.ConvertTo;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {
  
  @Autowired
  private AppUserService appUserService;

   @PostMapping("/save")
  public ResponseEntity<UserDTO> save(@RequestBody AppUser user) throws TheEntityAlredyExistsException, TheNecessaryEntitiesForTheOperationDoNotExistException{
   
    user.setAppUserRole(AppUserRole.USER);
    return ResponseEntity.ok(appUserService.save(user));
  }

  @GetMapping("/findByUsername")
  public ResponseEntity<UserDTO> findByUsername(@RequestParam String username){
    UserDetails user= appUserService.loadUserByUsername(username);
    return ResponseEntity.ok(ConvertTo.dto(user, UserDTO.class));
  }
}
