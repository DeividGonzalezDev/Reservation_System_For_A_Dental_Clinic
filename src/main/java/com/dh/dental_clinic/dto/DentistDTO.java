package com.dh.dental_clinic.dto;

import com.dh.dental_clinic.entity.HomeAddress;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DentistDTO {
  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private String surname;

  @Getter
  @Setter
  private String licenseNumber;

  @Getter
  @Setter
  private HomeAddress homeAddress;

  public DentistDTO() {
  }

  public DentistDTO(String name, String surname, String licenseNumber, HomeAddress homeAddress) {
    this.name = name;
    this.surname = surname;
    this.licenseNumber = licenseNumber;
    this.homeAddress = homeAddress;
  }
}
