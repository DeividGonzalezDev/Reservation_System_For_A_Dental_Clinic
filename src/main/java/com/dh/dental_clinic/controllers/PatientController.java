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

import com.dh.dental_clinic.dto.PatientDTO;
import com.dh.dental_clinic.entity.Patient;
import com.dh.dental_clinic.exceptions.NoEntityToDeleteException;
import com.dh.dental_clinic.exceptions.NoEntityToUpdateException;
import com.dh.dental_clinic.exceptions.TheEntityAlredyExistsException;
import com.dh.dental_clinic.services.impl.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {
  
  @Autowired
  private PatientService patientService;

  @GetMapping("/findAll")
  public ResponseEntity<List<PatientDTO>> findAll() {
    List<PatientDTO> patients = patientService.findAll();
    return ResponseEntity.ok(patients);
  }

  @GetMapping("/findById")
  public ResponseEntity<Optional<PatientDTO>> findById(@RequestParam UUID id){
    Optional<PatientDTO> patient = patientService.findById(id);
    return ResponseEntity.ok(patient);
  }

  @PostMapping("/save")
  public ResponseEntity<PatientDTO> save(@RequestBody Patient patient) throws TheEntityAlredyExistsException{
    return ResponseEntity.ok(patientService.save(patient));
  }

  @PutMapping("/update")
  public ResponseEntity<PatientDTO> update(@RequestBody Patient patient) throws NoEntityToUpdateException{
    return ResponseEntity.ok(patientService.update(patient));
  }

  @DeleteMapping("/deleteById")
  public ResponseEntity<Boolean> deleteById(@RequestParam UUID id) throws NoEntityToDeleteException{
    return ResponseEntity.ok(patientService.deleteById(id));
  }
}
