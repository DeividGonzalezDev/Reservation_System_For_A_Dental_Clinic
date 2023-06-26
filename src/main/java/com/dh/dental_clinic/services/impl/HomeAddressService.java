package com.dh.dental_clinic.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dh.dental_clinic.entity.HomeAddress;
import com.dh.dental_clinic.exceptions.NoEntityToUpdateException;
import com.dh.dental_clinic.repository.IHomeAddressRepository;
import com.dh.dental_clinic.services.IHomeAddressService;

import jakarta.validation.Valid;


/**
 * Servicio para operaciones relacionadas con entidades HomeAddress.
 */
@Service
public class HomeAddressService implements IHomeAddressService{

  @Autowired
  IHomeAddressRepository homeAddressRepository; // Inyección de dependencia del repositorio

  /**
   * Guarda una entidad HomeAddress en la base de datos.
   *
   * @param homeAddress La entidad HomeAddress a guardar.
   * @return La entidad HomeAddress guardada.
   * @throws IllegalArgumentException Si se proporciona un argumento inválido.
   */
  public HomeAddress save(HomeAddress homeAddress){
    return homeAddressRepository.save(homeAddress);
  }

  /**
   * Obtiene todas las entidades HomeAddress de la base de datos.
   *
   * @return Una lista de todas las entidades HomeAddress encontradas.
   */
  public List<HomeAddress> findAll() {
    List<HomeAddress> homeAddresses = homeAddressRepository.findAll();
    return homeAddresses;
  }

  /**
   * Busca una entidad HomeAddress por su ID en la base de datos.
   *
   * @param id El ID de la entidad HomeAddress a buscar.
   * @return Un Optional que contiene la entidad HomeAddress encontrada (si existe).
   */
  public Optional<HomeAddress> findById(UUID id) {
    Optional<HomeAddress> homeAddress = homeAddressRepository.findById(id);
    return homeAddress;
  }

  /**
   * Elimina una entidad HomeAddress por su ID de la base de datos.
   *
   * @param id El ID de la entidad HomeAddress a eliminar.
   * @return true si la eliminación se realiza correctamente, false en caso contrario.
   */
  public Boolean deleteById(UUID id) {
    Optional<HomeAddress> homeAddress = findById(id);
    if(homeAddress.isPresent()){
      homeAddressRepository.deleteById(id);
      return true;
    
    }
    return false;  
  }

  @Override
  public HomeAddress update(@Valid HomeAddress homeAddress) throws NoEntityToUpdateException {
    Optional<HomeAddress> homeAddressToUpdate = findById(homeAddress.getId());
    if(homeAddressToUpdate.isPresent()){
      return homeAddressRepository.save(homeAddress);
    }
    throw new NoEntityToUpdateException(homeAddress);
  }

  
}