package com.dh.dental_clinic.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dh.dental_clinic.entity.Dentist;

public interface IDentistRepository extends JpaRepository<Dentist, UUID>{

}
