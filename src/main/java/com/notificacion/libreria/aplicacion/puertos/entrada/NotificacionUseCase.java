package com.notificacion.libreria.aplicacion.puertos.entrada;

import com.notificacion.libreria.dominio.modelos.Notificacion;
import com.notificacion.libreria.dominio.modelos.ResultadoEnvio;
import com.notificacion.libreria.dominio.enums.TipoCanal;

public interface NotificacionUseCase {
    ResultadoEnvio enviar(Notificacion notificacion, TipoCanal canal);
}
