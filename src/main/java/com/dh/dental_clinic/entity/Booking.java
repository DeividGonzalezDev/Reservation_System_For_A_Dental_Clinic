package com.dh.dental_clinic.entity;

import java.sql.Date;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.dh.dental_clinic.dto.DentistDTO;
import com.dh.dental_clinic.dto.PatientDTO;
import com.dh.dental_clinic.utils.ConvertTo;
import com.dh.dental_clinic.utils.IdGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking {
  @Id
  private UUID id = UUID.randomUUID();

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date creationDate = new java.sql.Date(System.currentTimeMillis()); 

  @NotNull(message = "Este Campo no puede estar vacío")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date bookingDate;

  @Min(value = 1, message = "El turno minimo es 1")
  @Max(value = 10, message = "El turno maximo es 10")
  private int timeSlot;

  @NotNull(message = "Este Campo no puede estar vacío")
  @ManyToOne
  @JoinColumn(name = "dentist_id", referencedColumnName = "id")
  private Dentist dentist;

  
  @NotNull(message = "Este Campo no puede estar vacío")
  @ManyToOne
  @JoinColumn(name = "patient_id", referencedColumnName = "id")
  private Patient patient;

  
  public void setId() {
    this.id = IdGenerator.guid(bookingDate.toString() + timeSlot + dentist.getId() + patient.getId());
  }
  
  public void setDentist(Dentist dentist) {
    this.dentist = dentist;
  }
  
  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  /**
   * Establece el Dentista en la entidad actual utilizando el DentistDTO
   * proporcionado.
   *
   * @param dentistDTO el DTO que contiene los detalles del dentista
   */
  public void setDtoDentist(DentistDTO dentistDTO) {
    Map<String, Object> extraAtributes = new HashMap<>();

    UUID uuid = IdGenerator.guid(dentistDTO.getLicenseNumber());

    extraAtributes.put("id", uuid);

    Dentist dentist = ConvertTo.entity(dentistDTO, Dentist.class, extraAtributes);

    this.dentist = dentist;
  }

  /**
   * Establece el Paciente en la entidad actual  utilizando el PacienteDTO proporcionado.
   *
   * @param patientDTO el DTO que contiene los detalles del paciente
   */
  public void setDtoPatient(PatientDTO patientDTO) {
    Map<String, Object> extraAtributes = new HashMap<>();

    UUID uuid = IdGenerator.guid("" + patientDTO.getDni());

    extraAtributes.put("id", uuid);

    Patient patient = ConvertTo.entity(patientDTO, Patient.class, extraAtributes);

    this.patient = patient;
  }
}
