package com.notificacion.libreria.infraestructura.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.notificacion.libreria.aplicacion.puertos.salida.EstrategiaEnvio;
import com.notificacion.libreria.dominio.enums.TipoCanal;

class FabricaEnvioTest {

    private FabricaEnvio fabrica;

    @BeforeEach
    void setUp() {
        fabrica = new FabricaEnvio();
    }

    @Test
    void debeRegistrarYRecuperarEstrategiaCorrectamente() {
        EstrategiaEnvio emailMock = mock(EstrategiaEnvio.class);
        when(emailMock.obtenerTipoCanal()).thenReturn(TipoCanal.EMAIL);

        fabrica.registrarEstrategia(emailMock);
        
        assertEquals(emailMock, fabrica.obtenerEstrategia(TipoCanal.EMAIL));
    }

    @Test
    void debeLanzarExcepcionSiElCanalNoExiste() {
        assertThrows(IllegalArgumentException.class, () -> {
            fabrica.obtenerEstrategia(TipoCanal.SLACK);
        });
    }
}