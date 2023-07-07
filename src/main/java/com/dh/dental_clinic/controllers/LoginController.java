package com.dh.dental_clinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
  
  @GetMapping("/auth")
  public String login() {
    return "login.html";
  }

  @GetMapping("/sign-up")
  public String signUp() {
    return "signUp.html";
  }
}
