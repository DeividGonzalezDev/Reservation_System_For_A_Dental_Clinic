package com.dh.dental_clinic.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dh.dental_clinic.dto.DentistDTO;
import com.dh.dental_clinic.entity.Dentist;
import com.dh.dental_clinic.exceptions.NoEntityToDeleteException;
import com.dh.dental_clinic.exceptions.NoEntityToUpdateException;
import com.dh.dental_clinic.exceptions.TheEntityAlredyExistsException;
import com.dh.dental_clinic.services.impl.DentistService;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.log4j.Log4j2;


@RestController
@RequestMapping("/dentists")
@Log4j2
public class DentistController {

  @Autowired
  private DentistService dentistService;

  @GetMapping("/findAll")
  public ResponseEntity<List<DentistDTO>> findAll() {
    List<DentistDTO> dentists = dentistService.findAll();

    return ResponseEntity.ok(dentists);
  }

  @PostMapping("/save")
  public ResponseEntity<DentistDTO> save(@RequestBody Dentist dentist) throws TheEntityAlredyExistsException {
    return ResponseEntity.ok(dentistService.save(dentist));
  }

  @GetMapping("/findById")
  public ResponseEntity<Optional<DentistDTO>> findById(@RequestParam UUID id){
    Optional<DentistDTO> dentist = dentistService.findById(id);
    return ResponseEntity.ok(dentist);
  }

  @DeleteMapping("/deleteById")
  public ResponseEntity<Boolean> deleteById(@RequestParam UUID id) throws NoEntityToDeleteException{
    return ResponseEntity.ok(dentistService.deleteById(id));
  }

  @PutMapping("/update")
  public ResponseEntity<DentistDTO> update(@RequestBody Dentist dentist) throws NoEntityToUpdateException{
    return ResponseEntity.ok(dentistService.update(dentist));
  }
}
