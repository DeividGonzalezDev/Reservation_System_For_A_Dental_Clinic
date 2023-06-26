package com.dh.dental_clinic.dto;

import java.util.Map;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ErrorResponse {
  private int statusCode;
  private String message;
  @Nullable
  private Map<?,?> messageDetailsArguments;

  
  public ErrorResponse(int statusCode, String message) {
    this.statusCode = statusCode;
    this.message = message;
  }
}
