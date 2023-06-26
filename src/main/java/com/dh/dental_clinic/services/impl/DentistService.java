package com.dh.dental_clinic.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dh.dental_clinic.dto.DentistDTO;
import com.dh.dental_clinic.entity.Dentist;
import com.dh.dental_clinic.entity.HomeAddress;
import com.dh.dental_clinic.exceptions.NoEntityToDeleteException;
import com.dh.dental_clinic.exceptions.NoEntityToUpdateException;
import com.dh.dental_clinic.exceptions.TheEntityAlredyExistsException;
import com.dh.dental_clinic.repository.IDentistRepository;
import com.dh.dental_clinic.repository.IHomeAddressRepository;
import com.dh.dental_clinic.services.IDentistService;
import com.dh.dental_clinic.utils.ConvertTo;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

/**
 * Servicio para operaciones relacionadas con entidades Dentist.
 */
@Service
@Log4j2
public class DentistService implements IDentistService{

  @Autowired
  IDentistRepository dentistRepository; // Inyección de dependencia del repositorio
  @Autowired
  IHomeAddressRepository homeAddressRepository;

  /**
   * Crea un nuevo dentista en la base de datos.
   *
   * @param dentist El objeto Dentist que representa al dentista a crear.
   * @return El objeto DentistDTO creado.
   * @throws TheEntityAlredyExistsException
   */
  @Override
  public DentistDTO save(@Valid Dentist dentist) throws TheEntityAlredyExistsException {
    Optional<DentistDTO> dentistDTO = findById(dentist.getId());
    if(!(dentistDTO.isPresent())){
      HomeAddress homeAddress = dentist.getHomeAddress();
      if(!(homeAddress == null)){
        dentist.setHomeAddress(homeAddress);
        homeAddressRepository.save(homeAddress);
      }
      Dentist saveDentist = dentistRepository.save(dentist);
      return ConvertTo.dto(saveDentist, DentistDTO.class);
    }
    else{
      throw new TheEntityAlredyExistsException(dentist);
    }
  }

  /**
   * Obtiene todos los dentistas de la base de datos.
   *
   * @return Una lista de objetos DentistDTO que representan a todos los dentistas encontrados.
   */
  @Override
  public List<DentistDTO> findAll() {
    List<Dentist> dentists = dentistRepository.findAll();
    List<DentistDTO> dentistsDTO = ConvertTo.dto(dentists, DentistDTO.class);
    return dentistsDTO;
  }

  /**
   * Busca un dentista por su ID en la base de datos.
   *
   * @param id El ID del dentista a buscar.
   * @return El objeto DentistDTO que representa al dentista encontrado (si existe).
   */
  @Override
  public Optional<DentistDTO> findById(UUID id) {
    Optional<Dentist> dentist = dentistRepository.findById(id);
    log.info(dentist.toString());
    if(dentist.isPresent()){
      DentistDTO dentistDTO = ConvertTo.dto(dentist.get(), DentistDTO.class);
      return Optional.of(dentistDTO);
    }else{
      return Optional.empty();
    }
  }

  /**
   * Elimina un dentista de la base de datos.
   *
   * @param id El ID del dentista a eliminar.
   * @return true si la eliminación se realiza correctamente.
   * @throws NoEntityToDeleteException En caso de que el dentista no exista
   */
  @Override
  public Boolean deleteById(UUID id) throws NoEntityToDeleteException {
    Optional<DentistDTO> dentist = findById(id);
    if(dentist.isPresent()){
      dentistRepository.deleteById(id);
      return true;
    
    }
    throw new NoEntityToDeleteException(id);
  }

  /**
   * Actualiza un dentista en la base de datos.
   *
   * @param dentist El objeto Dentist que representa al dentista a actualizar.
   * @return El objeto DentistDTO actualizado.
   * @throws NoEntityToUpdateException en caso de que el dentista no exista
   */
  @Override
  public DentistDTO update(Dentist dentist) throws NoEntityToUpdateException{
    Optional<DentistDTO> dentistToUpdate = findById(dentist.getId());
    if(dentistToUpdate.isPresent()){
      HomeAddress homeAddress = dentist.getHomeAddress();
      if(!(homeAddress == null)){
        dentist.setHomeAddress(homeAddress);
        homeAddressRepository.save(homeAddress);
      }
      Dentist saveDentist = dentistRepository.save(dentist);
      return ConvertTo.dto(saveDentist, DentistDTO.class);
    }

    throw new NoEntityToUpdateException(dentist);
  }

}