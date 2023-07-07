package com.dh.dental_clinic.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dh.dental_clinic.entity.AppUserRole;
import com.dh.dental_clinic.filters.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure {

  @Autowired
  private AppUserService appUserService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

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
            .requestMatchers("/authenticate").permitAll()
            .requestMatchers("/auth").permitAll()
            .requestMatchers("/").permitAll()
            .requestMatchers("/user/**").permitAll()
            .requestMatchers("/booking/save").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
            .requestMatchers("/booking/findById").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
            .requestMatchers("/booking/update").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
            .requestMatchers("/booking/deleteById").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
            .requestMatchers("/dentists/findById").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
            .requestMatchers("/dentists/findAll").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
            .requestMatchers("/patient/save").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
            .requestMatchers("/patient/findById").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
            .requestMatchers("/patient/update").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
            .requestMatchers("/patient/deleteById").hasAnyAuthority(AppUserRole.USER.name(), AppUserRole.ADMIN.name())
            .requestMatchers("/dentists/**").hasAuthority(AppUserRole.ADMIN.name())
            .requestMatchers("/patient/**").hasAuthority(AppUserRole.ADMIN.name())
            .requestMatchers("/booking/**").hasAuthority(AppUserRole.ADMIN.name())
            .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).hasAuthority(AppUserRole.ADMIN.name())
            
            
            .anyRequest().permitAll()
            
            )

            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
            // .formLogin(Customizer.withDefaults())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers(headers -> headers
            .frameOptions(Customizer.withDefaults()).disable())
            // .formLogin(form -> form
            //   .loginPage("/auth")
            //   .defaultSuccessUrl("/")
            //   .permitAll()
            // );
            //.formLogin(Customizer.withDefaults())
            ;
            
            return http.build();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
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
