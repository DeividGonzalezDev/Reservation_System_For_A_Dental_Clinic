package com.dh.dental_clinic.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dh.dental_clinic.entity.Booking;

public interface IBookingRepository extends JpaRepository<Booking, UUID>{
  
}
