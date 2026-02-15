package com.notificaciones.libreria;

import org.junit.jupiter.api.Test;

import com.notificacion.libreria.NotificacionesApplication;

import static org.junit.jupiter.api.Assertions.*;

class NotificacionesApplicationTest {

    @Test
    void testMainExecution() {
        // Esta prueba verifica que el flujo completo del main no lance excepciones no controladas
        assertDoesNotThrow(() -> {
            NotificacionesApplication.main(new String[]{});
        }, "El método main debería ejecutarse sin lanzar excepciones inesperadas");
    }
}
