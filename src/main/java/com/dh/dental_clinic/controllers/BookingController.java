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

import com.dh.dental_clinic.dto.BookingDTO;
import com.dh.dental_clinic.entity.Booking;
import com.dh.dental_clinic.exceptions.NoEntityToUpdateException;
import com.dh.dental_clinic.services.impl.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {
  
  @Autowired
  private BookingService bookingService ;

 
  @GetMapping("/findAll")
  public ResponseEntity<List<BookingDTO>> findAll() {
    List<BookingDTO> bookings = bookingService.findAll();
    return ResponseEntity.ok(bookings);
  }

  @GetMapping("/findById")
  public ResponseEntity<Optional<BookingDTO>> findById(@RequestParam UUID id){
    Optional<BookingDTO> booking = bookingService.findById(id);
    return ResponseEntity.ok(booking);
  }

  @PostMapping("/save")
  public ResponseEntity<BookingDTO> save(@RequestBody Booking booking){
    return ResponseEntity.ok(bookingService.save(booking));
  }

  @PutMapping("/update") 
  public ResponseEntity<BookingDTO> update(@RequestBody Booking booking) throws NoEntityToUpdateException{
    return ResponseEntity.ok(bookingService.update(booking));
  }

  @DeleteMapping("/deleteById")
  public ResponseEntity<Boolean> deleteById(@RequestParam UUID id){
    return ResponseEntity.ok(bookingService.deleteById(id));
  }
}
