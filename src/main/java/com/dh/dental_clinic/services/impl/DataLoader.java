package com.dh.dental_clinic.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.dh.dental_clinic.entity.AppUser;
import com.dh.dental_clinic.entity.AppUserRole;
import com.dh.dental_clinic.entity.Dentist;
import com.dh.dental_clinic.entity.Patient;
import com.dh.dental_clinic.repository.IUserRepository;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class DataLoader implements ApplicationRunner {

  private IUserRepository userRepository;

  private PatientService patientService;

  private DentistService dentistService;

  @Autowired
  public DataLoader(IUserRepository userRepository, PatientService patientService, DentistService dentistService) {
    this.userRepository = userRepository;
    this.patientService = patientService;
    this.dentistService = dentistService;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    String password = bCryptPasswordEncoder.encode("admin");
    String password2 = bCryptPasswordEncoder.encode("user");

    Patient adminPatient = new Patient();
    adminPatient.setName("admin");
    adminPatient.setSurname("admin");
    adminPatient.setDni(87654321);

    
    patientService.save(adminPatient);

    Dentist dentist = new Dentist();
    dentist.setName("Juan");
    dentist.setSurname("Perez");
    dentist.setLicenseNumber("12345678");

    dentistService.save(dentist);

    userRepository.save(new AppUser("admin", "admin", "admin@gmail.com", password, AppUserRole.ADMIN));
    userRepository.save(new AppUser("user", "user", "user@gmail.com", password2, AppUserRole.USER));

  }

}
