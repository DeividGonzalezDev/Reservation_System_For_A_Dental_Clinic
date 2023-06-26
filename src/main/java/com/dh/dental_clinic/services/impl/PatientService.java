package com.dh.dental_clinic.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dh.dental_clinic.dto.PatientDTO;
import com.dh.dental_clinic.entity.HomeAddress;
import com.dh.dental_clinic.entity.Patient;
import com.dh.dental_clinic.exceptions.NoEntityToDeleteException;
import com.dh.dental_clinic.exceptions.NoEntityToUpdateException;
import com.dh.dental_clinic.exceptions.TheEntityAlredyExistsException;
import com.dh.dental_clinic.repository.IHomeAddressRepository;
import com.dh.dental_clinic.repository.IPatientRepository;
import com.dh.dental_clinic.services.IPatientService;
import com.dh.dental_clinic.utils.ConvertTo;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

/**
 * Servicio para operaciones relacionadas con entidades Patient.
 */
@Service
@Log4j2
public class PatientService implements IPatientService{
  
  @Autowired
  IPatientRepository patientRepository;

  @Autowired
  IHomeAddressRepository homeAddressRepository;

  /**
   * Crea un nuevo paciente en la base de datos.
   *
   * @param patient El objeto Patient que representa el paciente a crear.
   * @return El objeto PatientDTO creado.
   * @throws TheEntityAlredyExistsException
   */
  public PatientDTO save(@Valid Patient patient) throws TheEntityAlredyExistsException{
      Optional<PatientDTO> patientDTO = findById(patient.getId());
      log.info("encontrado: " + patient.toString());
    if(!(patientDTO.isPresent())){
      HomeAddress homeAddress = patient.getHomeAddress();
      log.info("homeAddress: " + homeAddress);
      if(!(homeAddress == null)){
        patient.setHomeAddress(homeAddress);
        homeAddressRepository.save(homeAddress);
      }
      Patient savePatient = patientRepository.save(patient);
      return ConvertTo.dto(savePatient, PatientDTO.class);
    }
    else{
      throw new TheEntityAlredyExistsException(patient);
    }
  }

  /**
   * Obtiene todos los pacientes de la base de datos.
   *
   * @return Una lista de objetos PatientDTO que representan a todos los pacientes encontrados.
   */
  public List<PatientDTO> findAll(){
    List<Patient> patients = patientRepository.findAll();
    List<PatientDTO> patientDTOs = ConvertTo.dto(patients, PatientDTO.class);
    return patientDTOs;
  }

  /**
   * Busca un paciente por su ID en la base de datos.
   *
   * @param id El ID del paciente a buscar.
   * @return El objeto PatientDTO que representa al paciente encontrado (si existe).
   */
 public Optional<PatientDTO> findById(UUID id){
  Optional<Patient> patient = patientRepository.findById(id);
  if(patient.isPresent()){
    PatientDTO patientDTO = ConvertTo.dto(patient.get(), PatientDTO.class);
    return Optional.of(patientDTO);
  }else{
    return Optional.empty();
  }
  }

  /**
   * Elimina un dentista de la base de datos.
   *
   * @param id El ID del dentista a eliminar.
   * @return true si la eliminación se realiza correctamente, false en caso contrario.
   * @throws NoEntityToDeleteException En caso de que el paciente no exista
   */
  public Boolean deleteById(UUID id)throws NoEntityToDeleteException {
    Optional<PatientDTO> patient = findById(id);
    if(patient.isPresent()){
      patientRepository.deleteById(id);
      return true;
    }
    throw new NoEntityToDeleteException(id);
  }
  /**
   * Actualiza un paciente en la base de datos.
   *
   * @param patient El objeto Patient que representa al paciente a actualizar.
   * @return El objeto PatientDTO actualizado.
   * @throws NoEntityToUpdateException En caso de que el paciente no exista
   */
  @Override
  public PatientDTO update(Patient patient) throws NoEntityToUpdateException{
    Optional<PatientDTO> patientToUpdate = findById(patient.getId());
    if(patientToUpdate.isPresent()){
      HomeAddress homeAddress = patient.getHomeAddress();
      if(!(homeAddress == null)){
        patient.setHomeAddress(homeAddress);
        homeAddressRepository.save(homeAddress);
      }
      PatientDTO patientDTO = ConvertTo.dto(patientRepository.save(patient), PatientDTO.class);
      return patientDTO;
    }
    throw new NoEntityToUpdateException(patient);
  }
}
