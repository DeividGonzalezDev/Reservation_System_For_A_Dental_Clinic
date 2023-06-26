package com.dh.dental_clinic.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import com.dh.dental_clinic.utils.IdGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Patient {

  @Id
  private UUID id;

  @NotBlank(message = "Este Campo no puede estar vacío")
  private String name;

  @NotBlank(message = "Este Campo no puede estar vacío")
  private String surname;

  @NotNull(message = "Este Campo no puede estar vacío")
  private int dni;

   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date dischargedDate = new Date();

  @ManyToOne
  @JoinColumn(name = "home_address_id", referencedColumnName = "id")
  private HomeAddress homeAddress;


  // public void setDischargedDate(String dischargedDate){
  //     this.dischargedDate = LocalDate.parse(dischargedDate);
  // }

  public void setDni(int dni){
    this.dni = dni;
    if(this.id == null){
      this.id = IdGenerator.guid(""+dni);
    }
  }

}
