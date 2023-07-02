package com.dh.dental_clinic.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.dh.dental_clinic.entity.AppUserRole;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure {

  @Autowired
  private AppUserService appUserService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  // @Bean
  // public SecurityFilterChain configure(HttpSecurity http) throws Exception {
  // http.authorizeHttpRequests()
  // .requestMatchers("/**")
  // .hasRole(AppUserRole.USER.name())
  // .and()
  // .formLogin();

  // return http.build();
  // }

  // @Bean
  // public UserDetailsService userDetailsService() {
  // UserDetails user = User.bCryptPasswordEncoder.encode("user")
  // .username("user")
  // .password("password")
  // .roles("USER")
  // .build();
  // return new InMemoryUserDetailsManager(user);
  // }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests((authz) -> authz
            .requestMatchers("/booking/save").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
            .requestMatchers("/booking/findById").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
            .requestMatchers("/booking/update").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
            .requestMatchers("/booking/deleteById").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
            .requestMatchers("/dentists/findById").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
            .requestMatchers("/dentists/findAll").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
            .requestMatchers("/dentists/**").hasAuthority(AppUserRole.ADMIN.name())
            .requestMatchers("/patient/**").hasAuthority(AppUserRole.ADMIN.name())
            .requestMatchers("/booking/**").hasAuthority(AppUserRole.ADMIN.name())

            .anyRequest().authenticated())
        .formLogin(Customizer.withDefaults())
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
        .headers(headers -> headers
            .frameOptions(Customizer.withDefaults()).disable());

    return http.build();
  }

  // protected void configure(AuthenticationManagerBuilder auth) throws Exception
  // {
  // auth.authenticationProvider(daoAuthenticationProvider());
  // }

  // @Bean
  // public DaoAuthenticationProvider daoAuthenticationProvider() {
  // DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
  // provider.setPasswordEncoder(bCryptPasswordEncoder);
  // provider.setUserDetailsService(appUserService);
  // return provider;
  // }

}
