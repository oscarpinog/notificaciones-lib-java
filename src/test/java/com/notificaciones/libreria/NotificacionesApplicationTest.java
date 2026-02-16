package com.notificaciones.libreria;

import org.junit.jupiter.api.Test;

import com.notificacion.libreria.NotificacionesApplication;

import static org.junit.jupiter.api.Assertions.*;

class NotificacionesApplicationTest {

	@Test
    void testMainExecution() {
        // No lanzará excepción porque ahora el main tiene un valor por defecto ("EMAIL")
        // si la variable de entorno es nula.
        assertDoesNotThrow(() -> {
            NotificacionesApplication.main(new String[]{});
        }, "El método main debería ejecutarse sin lanzar excepciones inesperadas");
    }
}
