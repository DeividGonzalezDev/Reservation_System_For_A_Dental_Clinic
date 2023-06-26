package com.dh.dental_clinic.dto;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientDTO {
  @Getter @Setter
  private String name;

  @Getter @Setter
  private String surname;

  @Getter @Setter
  private int dni;

  @Getter @Setter
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date dischargedDate;

  public PatientDTO() {
  }

  public PatientDTO(String name, String surname, int dni, Date dischargedDate) {
    this.name = name;
    this.surname = surname;
    this.dni = dni;
    this.dischargedDate = dischargedDate;
  }
}
