package com.notificacion.libreria.aplicacion.servicios;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.notificacion.libreria.aplicacion.puertos.salida.EstrategiaEnvio;
import com.notificacion.libreria.dominio.enums.TipoCanal;
import com.notificacion.libreria.dominio.excepciones.ValidacionException;
import com.notificacion.libreria.dominio.modelos.Notificacion;
import com.notificacion.libreria.dominio.modelos.ResultadoEnvio;
import com.notificacion.libreria.infraestructura.factory.FabricaEnvio;


@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    @Mock
    private FabricaEnvio fabricaMock;

    @Mock
    private EstrategiaEnvio estrategiaMock;

    private NotificacionService service;

    @BeforeEach
    void setUp() {
        service = new NotificacionService(fabricaMock);
    }

    @Test
    void debeLanzarExcepcionSiEmailNoTieneSubject() {
        Notificacion noti = new Notificacion.Builder()
                .para("test@test.com")
                .conMensaje("Mensaje sin subject")
                .construir(); // No agregamos el atributo "subject"

        assertThrows(ValidacionException.class, () -> {
            service.enviar(noti, TipoCanal.EMAIL);
        });
    }

    @Test
    void debeLlamarAlMetodoEnviarDeLaEstrategiaCorrecta() {
        // GIVEN: Una notificación válida y una fábrica que devuelve un mock de estrategia
        Notificacion noti = new Notificacion.Builder()
                .para("3001234567")
                .conMensaje("Mensaje SMS")
                .construir();
        
        when(fabricaMock.obtenerEstrategia(TipoCanal.SMS)).thenReturn(estrategiaMock);
        when(estrategiaMock.notificar(noti)).thenReturn(new ResultadoEnvio(true, "ID-1", "OK", "Provider",null));

        // WHEN: Ejecutamos el envío
        ResultadoEnvio resultado = service.enviar(noti, TipoCanal.SMS);

        // THEN: Verificamos que se llamó a la estrategia y el resultado es exitoso
        assertTrue(resultado.exitoso());
        verify(estrategiaMock, times(1)).notificar(noti);
    }



    @Test
    void debeLanzarExcepcionCuandoEmailNoTieneSubject() {
        Notificacion noti = new Notificacion.Builder()
                .para("test@correo.com")
                .conMensaje("Cuerpo del mensaje")
                // No agregamos el atributo "subject"
                .construir();

        ValidacionException ex = assertThrows(ValidacionException.class, () -> {
            service.enviar(noti, TipoCanal.EMAIL);
        });
        
        assertTrue(ex.getMessage().contains("requiere un atributo 'subject'"));
    }
   

    // CASO EXITOSO: Datos correctos
//    @Test
//    void debePasarValidacionCuandoEmailEsCorrectoConSubject() {
//        Notificacion noti = new Notificacion.Builder()
//                .para("valido@test.com")
//                .conMensaje("Mensaje OK")
//                .conAtributo("subject", "Asunto importante")
//                .construir();
//
//        // Necesitamos mockear la fábrica para que no explote al buscar la estrategia
//        EstrategiaEnvio estrategiaMock = mock(EstrategiaEnvio.class);
//        when(fabricaMock.obtenerEstrategia(TipoCanal.EMAIL)).thenReturn(estrategiaMock);
//
//        // Si no lanza excepción, el test pasa
//        assertDoesNotThrow(() -> service.enviar(noti, TipoCanal.EMAIL));
//    }
    
    @Test
    void debeLanzarExcepcionCuandoEmailNoTieneArroba() {
        Notificacion noti = new Notificacion.Builder()
                .para("usuario-sin-arroba.com")
                .conMensaje("Contenido")
                .construir();

        ValidacionException ex = assertThrows(ValidacionException.class, () -> {
            service.enviar(noti, TipoCanal.EMAIL);
        });
        
        assertTrue(ex.getMessage().contains("Formato de email inválido"));
    }
    
}