package com.dh.dental_clinic.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Clase de utilidad para generar IDs basados en un texto.
 */
public class IdGenerator {

  /**
   * Genera un ID único basado en el texto proporcionado.
   * Utiliza el Algoritmo SHA-256
   *
   * @param text El texto utilizado para generar el ID.
   * @return El ID generado.
   * @throws RuntimeException Si ocurre un error durante la generación del ID.
   */
  public static Long sha256(String text) throws RuntimeException {
    try {
      // Crear instancia de MessageDigest con el algoritmo SHA-256
      MessageDigest digest = MessageDigest.getInstance("SHA-256");

      // Obtener el arreglo de bytes del texto
      byte[] encodedHash = digest.digest(text.getBytes(StandardCharsets.UTF_8));

      // Convertir los bytes a representación hexadecimal
      StringBuilder hexString = new StringBuilder();
      for (byte b : encodedHash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) {
          hexString.append('0');
        }
        hexString.append(hex);
      }

      // Devolver el ID generado
      return Long.parseLong(hexString.toString(), 16);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

public static UUID guid(String string) {
    return UUID.nameUUIDFromBytes(string.getBytes());
}


}

