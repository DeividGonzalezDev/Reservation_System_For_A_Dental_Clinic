package com.dh.dental_clinic.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dh.dental_clinic.dto.DentistDTO;
import com.dh.dental_clinic.entity.Dentist;
import com.dh.dental_clinic.entity.HomeAddress;
import com.dh.dental_clinic.services.impl.DentistService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class DentistControllerTest {

  @InjectMocks
  private DentistController dentistController;

  @Mock
  private DentistService dentistService;

  private MockMvc mockMvc;

  private Dentist dentist;

  private DentistDTO dentistDTO;

  @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dentistController).build();

        dentist = new Dentist();
        dentist.setLicenseNumber("12345678");
        dentist.setName("Juan");
        dentist.setSurname("Perez");
        dentist.setHomeAddress(new HomeAddress("Calle 123", "123", "Hear", "Tacuarembo"));


        dentistDTO = new DentistDTO("Juan", "Perez", "12345678", new HomeAddress("Calle 123", "123", "Hear", "Tacuarembo"));
    }

  @Test
  public void testFindAll() throws Exception {
    // Test case: Finding all dentists
    List<DentistDTO> expectedDentists = new ArrayList<>();
    expectedDentists.add(dentistDTO);
    expectedDentists.add(new DentistDTO());

    when(dentistService.findAll()).thenReturn(expectedDentists);

    mockMvc.perform(MockMvcRequestBuilders.get("/dentists/findAll"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
  }

  @Test
  public void testSave() throws Exception {
    // Test case: Saving a dentist
    when(dentistService.save(any(Dentist.class))).thenReturn(dentistDTO);

    mockMvc.perform(MockMvcRequestBuilders.post("/dentists/save")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(dentist)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Juan")));
  }

  @Test
  public void testFindById() throws Exception {
    // Test case: Finding a dentist by ID
    when(dentistService.findById(any(UUID.class))).thenReturn(Optional.of(dentistDTO));
    
    mockMvc.perform(MockMvcRequestBuilders.get("/dentists/findById?id=" + UUID.randomUUID()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Juan")));
  }

  @Test
  public void testDeleteById() throws Exception {
    // Test case: Deleting a dentist by ID
    when(dentistService.deleteById(any(UUID.class))).thenReturn(true);

    mockMvc.perform(MockMvcRequestBuilders.delete("/dentists/deleteById?id=" + UUID.randomUUID()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(true)));

    Mockito.verify(dentistService).deleteById(any(UUID.class));
  }

  @Test
  public void testUpdate() throws Exception {
    // Test case: Updating a dentist
    dentistDTO.setName("Updated Juan");
    when(dentistService.update(any(Dentist.class))).thenReturn(dentistDTO);

    mockMvc.perform(MockMvcRequestBuilders.put("/dentists/update")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(dentist)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Updated Juan")));
  }

}
