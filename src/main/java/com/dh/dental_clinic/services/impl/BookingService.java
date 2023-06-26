package com.dh.dental_clinic.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dh.dental_clinic.dto.BookingDTO;
import com.dh.dental_clinic.entity.Booking;
import com.dh.dental_clinic.exceptions.NoEntityToUpdateException;
import com.dh.dental_clinic.exceptions.TheEntityAlredyExistsException;
import com.dh.dental_clinic.exceptions.TheNecessaryEntitiesForTheOperationDoNotExistException;
import com.dh.dental_clinic.repository.IBookingRepository;
import com.dh.dental_clinic.services.IBookingService;
import com.dh.dental_clinic.utils.ConvertTo;

import jakarta.validation.Valid;

@Service
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
   */
  @Override
  public BookingDTO save(@Valid Booking booking) throws TheEntityAlredyExistsException {
    booking.setId();
    Optional<BookingDTO> bookingDTO = findById(booking.getId());
    if (!(bookingDTO.isPresent())) {
      if(dentistService.findById(booking.getDentist().getId()).isPresent() && patientService.findById(booking.getPatient().getId()).isPresent()){
        booking.setDentist(dentistService.findById(booking.getDentist().getId()).get());
        booking.setPatient(patientService.findById(booking.getPatient().getId()).get());
        Booking saveBooking = bookingRepository.save(booking);
        return ConvertTo.dto(saveBooking, BookingDTO.class);
      }else{
        Map<String, Object> errors = new HashMap<>();
        errors.put("dentist", "El dentista no existe");
        throw new TheNecessaryEntitiesForTheOperationDoNotExistException(errors);
      }

      
    }else{
      throw new TheEntityAlredyExistsException(booking);
    }
  }

  @Override
  public List<BookingDTO> findAll() {
    List<Booking> bookings = bookingRepository.findAll();
    List<BookingDTO> bookingsDTO = ConvertTo.dto(bookings, BookingDTO.class);
    return bookingsDTO;
  }

  @Override
  public Optional<BookingDTO> findById(UUID id) {
    Optional<Booking> booking = bookingRepository.findById(id);
    if (booking.isPresent()) {
      BookingDTO bookingDTO = ConvertTo.dto(booking, BookingDTO.class);
      return Optional.of(bookingDTO);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Boolean deleteById(UUID id) {
    Optional<BookingDTO> booking = findById(id);
    if (booking.isPresent()) {
      bookingRepository.deleteById(id);
      return true;

    }
    return false;
  }

  @Override
  public BookingDTO update(@Valid Booking booking) throws NoEntityToUpdateException {
    Optional<BookingDTO> bookingToUpdate = findById(booking.getId());
    if (bookingToUpdate.isPresent()) {
      BookingDTO BookingDTO = ConvertTo.dto(bookingRepository.save(booking), BookingDTO.class);
      return BookingDTO;
    }
    throw new NoEntityToUpdateException(booking);
  }

  // @Override
  // public BookingDTO updateSomeParameters(Booking booking) throws
  // NoEntityToUpdateException {
  // //Crea la funcionalidad para actualizar los parámetros de la entidad pasada
  // por parámetro, trae la entidad de la base de datos y actualiza los
  // parámetros, con los parámetros de la entidad pasada por parámetro.
  // Optional<BookingDTO> bookingToUpdate = findById(booking.getId());
  // if(bookingToUpdate.isPresent()){
  // BookingDTO BookingDTO = ConvertTo.dto(bookingRepository.save(t),
  // BookingDTO.class);
  // return BookingDTO;
  // }
  // throw new NoEntityToUpdateException(booking);
  // }

}
