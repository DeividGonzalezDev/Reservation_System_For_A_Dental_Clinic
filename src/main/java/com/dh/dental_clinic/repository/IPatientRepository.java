package com.dh.dental_clinic.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dh.dental_clinic.entity.Patient;

public interface IPatientRepository extends JpaRepository<Patient, UUID> {
  
}
