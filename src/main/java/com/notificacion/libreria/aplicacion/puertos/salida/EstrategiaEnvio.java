package com.notificacion.libreria.aplicacion.puertos.salida;

import com.notificacion.libreria.dominio.modelos.Notificacion;
import com.notificacion.libreria.dominio.modelos.ResultadoEnvio;
import com.notificacion.libreria.dominio.enums.TipoCanal;

public interface EstrategiaEnvio {
    ResultadoEnvio notificar(Notificacion notificacion);
    TipoCanal obtenerTipoCanal();
    String obtenerNombreProveedor();
}