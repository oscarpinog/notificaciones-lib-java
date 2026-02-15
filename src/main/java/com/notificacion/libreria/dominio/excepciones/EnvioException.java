package com.notificacion.libreria.dominio.excepciones;

public class EnvioException extends RuntimeException {
    public EnvioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}