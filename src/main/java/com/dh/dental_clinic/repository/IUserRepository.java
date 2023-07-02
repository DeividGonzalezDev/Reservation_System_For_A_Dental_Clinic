package com.dh.dental_clinic.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dh.dental_clinic.entity.AppUser;


@Repository
@Transactional(readOnly = true)
public interface IUserRepository extends JpaRepository<AppUser, UUID>{
  
  public Optional<AppUser> findByUsername(String username);
}
