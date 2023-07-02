package com.dh.dental_clinic.services;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.dh.dental_clinic.services.impl.DentistService;
import com.dh.dental_clinic.services.impl.HomeAddressService;

import lombok.extern.log4j.Log4j2;

import com.dh.dental_clinic.dto.DentistDTO;
import com.dh.dental_clinic.entity.Dentist;
import com.dh.dental_clinic.entity.HomeAddress;
import com.dh.dental_clinic.exceptions.*;
import com.dh.dental_clinic.repository.IDentistRepository;
import com.dh.dental_clinic.repository.IHomeAddressRepository;

import org.hibernate.engine.jdbc.env.spi.SQLStateType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Log4j2
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DentistServiceTest {

  @InjectMocks
  private DentistService dentistService;

  @Mock
  private IDentistRepository dentistRepository;

  @Mock
  private HomeAddressService homeAddressService;

  @Mock
  private DentistService dentistServiceMock;

  private Dentist dentist;

   @BeforeEach
    public void setUp() {
        // Crea una instancia de Dentist antes de cada test
        dentist = new Dentist();
        dentist.setLicenseNumber("12345678");
        dentist.setName("Juan");
        dentist.setSurname("Perez");
        dentist.setHomeAddress(new HomeAddress("Calle 123", "123", "Hear", "Tacuarembo"));
    }

  @Test
  public void testSaveWithNewDentist() throws TheEntityAlredyExistsException {

    // Configurar el comportamiento esperado del repositorio
    when(dentistRepository.save(any(Dentist.class))).thenReturn(dentist);
    when(dentistRepository.findById(UUID.randomUUID())).thenReturn(Optional.empty());

    // Llamar al método que se está probando
    DentistDTO savedDentist = dentistService.save(dentist);

    // Verificar los resultados esperados
    assertNotNull(savedDentist);
    assertEquals(dentist.getLicenseNumber(), savedDentist.getLicenseNumber());
    // ...

    // Verificar que el método save() del repositorio se haya llamado con los
    // argumentos correctos
    verify(dentistRepository).save(any(Dentist.class));
  }

  @Test
  public void testSaveWithExistingDentist() throws
  TheEntityAlredyExistsException {

  // comportamiento esperado del repositorio
  when(dentistRepository.save(any(Dentist.class))).thenReturn(dentist);
  when(dentistRepository.findById(any(UUID.class))).thenReturn(Optional.of(dentist));

  try {
  // Llama al método que se está probando
  DentistDTO savedDentist = dentistService.save(dentist);
    fail("The Save() method was expected to launch an exception of theEntityalredyexistsexception");
  } catch (TheEntityAlredyExistsException e) {
  assertEquals("TheEntityAlredyExistsException", e.getClass().getSimpleName());

  assertEquals(dentist, e.getEntity());
  }
  }

  @Test
    public void testFindAll() {
      when(dentistRepository.findAll()).thenReturn(Arrays.asList(dentist));

      List<DentistDTO> dentists = dentistService.findAll();

      assertNotNull(dentists);
    }

    @Test
    public void testFindById() {
      when(dentistRepository.findById(any(UUID.class))).thenReturn(Optional.of(dentist));

      Optional<DentistDTO> dentist = dentistService.findById(UUID.randomUUID());

      assertNotNull(dentist);


    }

    @Test
    public void testDeleteById() throws NoEntityToDeleteException {
      when(dentistRepository.findById(any(UUID.class))).thenReturn(Optional.of(dentist));

      Boolean deleted = dentistService.deleteById(UUID.randomUUID());

      assertTrue(deleted);
    }

    @Test
    public void testUpdate() throws NoEntityToUpdateException {
      dentist.setName("Updated Juanito");
       when(dentistRepository.findById(any(UUID.class))).thenReturn(Optional.of(dentist));
       when(dentistRepository.save(any(Dentist.class))).thenReturn(dentist);

       DentistDTO dentistDTO = dentistService.update(dentist);

       assertNotNull(dentistDTO);
       assertEquals(dentist.getName(), dentistDTO.getName());
    }

}
