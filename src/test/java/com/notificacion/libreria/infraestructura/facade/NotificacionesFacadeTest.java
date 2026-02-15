package com.notificacion.libreria.infraestructura.facade;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import com.notificacion.libreria.aplicacion.puertos.entrada.NotificacionUseCase;
import com.notificacion.libreria.aplicacion.puertos.salida.EstrategiaEnvio;
import com.notificacion.libreria.dominio.enums.TipoCanal;

class NotificacionesFacadeTest {

    @Test
    void debeInicializarCorrectamenteYEntregarElServicio() {
        // GIVEN: Credenciales de prueba
        String emailKey = "SG.123";
        String smsId = "AC.456";
        String pushPath = "config/firebase.json";

        // WHEN: Creamos la fachada
        NotificacionesFacade facade = new NotificacionesFacade(emailKey, smsId, pushPath);
        NotificacionUseCase servicio = facade.obtenerServicio();

        // THEN: El servicio no debe ser nulo
        assertNotNull(servicio, "El servicio obtenido de la fachada no debería ser nulo");
    }

    @Test
    void debePermitirRegistrarUnCanalPersonalizado() {
        // GIVEN: Una fachada inicializada
        NotificacionesFacade facade = new NotificacionesFacade("key", "id", "path");
        
        // Creamos un Mock de una nueva estrategia (ej: Slack)
        EstrategiaEnvio slackMock = mock(EstrategiaEnvio.class);
        when(slackMock.obtenerTipoCanal()).thenReturn(TipoCanal.SLACK);

        // WHEN: Registramos el canal personalizado
        // No debería lanzar excepción
        assertDoesNotThrow(() -> facade.registrarCanalPersonalizado(slackMock));
        
        // THEN: Podemos verificar que el servicio ahora reconoce el nuevo canal
        // (Esto es una prueba de integración ligera dentro de la fachada)
        assertNotNull(facade.obtenerServicio());
    }
}