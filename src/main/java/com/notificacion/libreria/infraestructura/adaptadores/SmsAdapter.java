package com.notificacion.libreria.infraestructura.adaptadores;

import com.notificacion.libreria.aplicacion.puertos.salida.EstrategiaEnvio;
import com.notificacion.libreria.dominio.enums.TipoCanal;
import com.notificacion.libreria.dominio.modelos.Notificacion;
import com.notificacion.libreria.dominio.modelos.ResultadoEnvio;
import java.util.UUID;

public class SmsAdapter implements EstrategiaEnvio {
    private final String accountSid;

    public SmsAdapter(String accountSid) {
        this.accountSid = accountSid;
    }

    @Override
    public ResultadoEnvio notificar(Notificacion notificacion) {

        System.out.println("||*******************INICIO SMS ADAPTER************************||");

        return new ResultadoEnvio.Builder()
        .exitoso(true)
        .mensajeId(UUID.randomUUID().toString())
        .detalle("SMS entregado a la red telefónica -"+accountSid)
        .proveedor(obtenerNombreProveedor())
        .construir();
        
    }

    @Override
    public TipoCanal obtenerTipoCanal() {
        return TipoCanal.SMS;
    }

    @Override
    public String obtenerNombreProveedor() {
        return "Movistar";
    }
}
