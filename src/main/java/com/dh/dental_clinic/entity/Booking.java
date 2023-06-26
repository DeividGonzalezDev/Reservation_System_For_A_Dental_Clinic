package com.dh.dental_clinic.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.dh.dental_clinic.dto.DentistDTO;
import com.dh.dental_clinic.dto.PatientDTO;
import com.dh.dental_clinic.utils.ConvertTo;
import com.dh.dental_clinic.utils.IdGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking {
  @Id
  private UUID id = UUID.randomUUID();

  private Date creationDate = new Date();

  @NotNull(message = "Este Campo no puede estar vacío")
  private Date BookingDate;

  @Pattern(regexp = "^[1-9]|10$", message = "Los turnos validos son: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10")
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
    this.id = IdGenerator.guid(BookingDate.toString() + timeSlot + dentist.getId() + patient.getId());
  }

  /**
   * Establece el Dentista en la entidad actual utilizando el DentistDTO
   * proporcionado.
   *
   * @param dentistDTO el DTO que contiene los detalles del dentista
   */
  public void setDentist(DentistDTO dentistDTO) {
    Map<String, UUID> extraAtributes = new HashMap<>();

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
  public void setPatient(PatientDTO patientDTO) {
    Map<String, UUID> extraAtributes = new HashMap<>();

    UUID uuid = IdGenerator.guid("" + patientDTO.getDni());

    extraAtributes.put("id", uuid);

    Patient patient = ConvertTo.entity(patientDTO, Patient.class, extraAtributes);

    this.patient = patient;
  }
}
