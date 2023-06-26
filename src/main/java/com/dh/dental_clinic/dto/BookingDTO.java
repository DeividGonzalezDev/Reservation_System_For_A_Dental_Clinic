package com.dh.dental_clinic.dto;

import java.time.LocalDate;

import com.dh.dental_clinic.entity.Dentist;
import com.dh.dental_clinic.entity.Patient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor
public class BookingDTO {
 private LocalDate bookingDate;
 private Dentist dentistId;
 private Patient patientId;


}
