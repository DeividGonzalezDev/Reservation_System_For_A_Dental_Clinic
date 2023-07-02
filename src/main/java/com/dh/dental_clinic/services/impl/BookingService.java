package com.dh.dental_clinic.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dh.dental_clinic.dto.BookingDTO;
import com.dh.dental_clinic.dto.DentistDTO;
import com.dh.dental_clinic.dto.PatientDTO;
import com.dh.dental_clinic.entity.Booking;
import com.dh.dental_clinic.exceptions.NoEntityToDeleteException;
import com.dh.dental_clinic.exceptions.NoEntityToUpdateException;
import com.dh.dental_clinic.exceptions.TheEntityAlredyExistsException;
import com.dh.dental_clinic.exceptions.TheNecessaryEntitiesForTheOperationDoNotExistException;
import com.dh.dental_clinic.repository.IBookingRepository;
import com.dh.dental_clinic.services.IBookingService;
import com.dh.dental_clinic.utils.ConvertTo;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BookingService implements IBookingService {

  @Autowired
  IBookingRepository bookingRepository;

  @Autowired
  private DentistService dentistService;

  @Autowired
  private PatientService patientService;

  /**
   * Crea una nueva reserva en la base de datos.
   *
   * @param booking El objeto Booking que representa la reserva a crear.
   * @return El objeto BookingDTO creado.
   * @throws TheEntityAlredyExistsException
   * @throws TheNecessaryEntitiesForTheOperationDoNotExistException
   */
  @Override
  public BookingDTO save(@Valid Booking booking)
      throws TheEntityAlredyExistsException, TheNecessaryEntitiesForTheOperationDoNotExistException {
    booking.setId();
    log.info(booking);
    Optional<BookingDTO> bookingDTO = findById(booking.getId());
    if (!(bookingDTO.isPresent())) {

      Optional<DentistDTO> dentist = dentistService.findById(booking.getDentist().getId());
      Optional<PatientDTO> patient = patientService.findById(booking.getPatient().getId());

      if (dentist.isPresent() && patient.isPresent()) {
        booking.setDtoDentist(dentist.get());
        booking.setDtoPatient(patient.get());
        log.info(booking.toString());
        Booking savedBooking = bookingRepository.save(booking);
        log.info("THE ULTIMATE BOOKING: " + savedBooking.toString());
        return ConvertTo.dto(savedBooking, BookingDTO.class);
      } else {
        Map<String, Object> errors = new HashMap<>();
        errors.put("dentist", booking.getDentist());
        errors.put("patient", booking.getPatient());
        throw new TheNecessaryEntitiesForTheOperationDoNotExistException(errors);
      }

    } else {
      throw new TheEntityAlredyExistsException(booking);
    }
  }

  /**
   * Obtiene todas las reservas en la base de datos.
   * 
   * @return List<BookingDTO> Una lista de objetos BookingDTO que representan
   *         todas las reservas encontradas
   */
  @Override
  public List<BookingDTO> findAll() {
    List<Booking> bookings = bookingRepository.findAll();
    List<BookingDTO> bookingsDTO = ConvertTo.dto(bookings, BookingDTO.class);
    return bookingsDTO;
  }

  /**
   * Obtiene una reserva en la base de datos por su id.
   * 
   * @param id El id de la reserva a buscar
   * @return Optional<BookingDTO> El objeto BookingDTO que representa la reserva
   *         encontrada
   */
  @Override
  public Optional<BookingDTO> findById(UUID id) {
    Optional<Booking> booking = bookingRepository.findById(id);
    if (booking.isPresent()) {
      BookingDTO bookingDTO = ConvertTo.dto(booking.get(), BookingDTO.class);
      log.info("BOOKING DTO: " + bookingDTO.toString());
      return Optional.of(bookingDTO);
    } else {
      return Optional.empty();
    }
  }

  /**
   * Elimina una reserva de la base de datos.
   * 
   * @param id El id de la reserva a eliminar
   * @return Boolean True si la reserva fue eliminada con éxito.
   * @throws NoEntityToDeleteException En caso de que la reserva no exista
   */
  @Override
  public Boolean deleteById(UUID id) throws NoEntityToDeleteException {
    Optional<BookingDTO> booking = findById(id);
    if (booking.isPresent()) {
      bookingRepository.deleteById(id);
      return true;

    }
    throw new NoEntityToDeleteException(id);
  }

  @Override
  public BookingDTO update(@Valid Booking booking)
      throws NoEntityToUpdateException, TheNecessaryEntitiesForTheOperationDoNotExistException {
    if (booking.getId() == null) {
      booking.setId();
    }
    Optional<BookingDTO> bookingToUpdate = findById(booking.getId());
    if (bookingToUpdate.isPresent()) {

      Optional<DentistDTO> dentist = dentistService.findById(booking.getDentist().getId());
      Optional<PatientDTO> patient = patientService.findById(booking.getPatient().getId());

      if (dentist.isPresent() && patient.isPresent()) {
        booking.setDtoDentist(dentist.get());
        booking.setDtoPatient(patient.get());
        Booking savedBooking = bookingRepository.save(booking);
        return ConvertTo.dto(savedBooking, BookingDTO.class);
      } else {
        Map<String, Object> errors = new HashMap<>();
        errors.put("dentist", booking.getDentist());
        errors.put("patient", booking.getPatient());
        throw new TheNecessaryEntitiesForTheOperationDoNotExistException(errors);
      }

    }else{
      throw new NoEntityToUpdateException(booking);

    }
  }


}
