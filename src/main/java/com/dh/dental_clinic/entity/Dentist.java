package com.dh.dental_clinic.entity;

import java.util.UUID;


import com.dh.dental_clinic.utils.IdGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Entity
@ToString
@NoArgsConstructor
@Getter @Setter
public class Dentist {
  
  @Id
  private UUID id;
  
  @NotBlank(message = "Este campo no puede estar vacío")
  private String name;

  @NotBlank(message = "Este Campo no puede estar vacío")
  private String surname;

  @NotBlank(message = "Este Campo no puede estar vacío")
  private String licenseNumber;
  
  @ManyToOne
  @JoinColumn(name = "home_address_id", referencedColumnName = "id")
  private HomeAddress homeAddress;
  
  //Setter de LicenseNumber, Si el cliente no envía el id, se genera uno nuevo en base a la matricula, Esto tiene muchas ventajas.
  public void setLicenseNumber(String licenseNumber){
    this.licenseNumber = licenseNumber;
    if(this.id == null){
      this.id = IdGenerator.guid(licenseNumber);
    }
  }
}
