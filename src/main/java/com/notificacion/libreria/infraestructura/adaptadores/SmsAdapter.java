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
        // En SMS podrías validar que el receptor sea un número de teléfono
        if (!notificacion.getReceptor().startsWith("+")) {
            System.out.println("[SMS Warning] El receptor " + notificacion.getReceptor() + " no tiene formato internacional.");
        }
        System.out.println("||*******************INICIO SMS ADAPTER************************||");
        
        System.out.println("[SMS] Enviando via Movistar (SID: " + accountSid + ")");
        System.out.println("      Para: " + notificacion.getReceptor());
        System.out.println("      Texto: " + notificacion.getContenido());

        return new ResultadoEnvio(
            true, 
            UUID.randomUUID().toString(), 
            "SMS entregado a la red telefónica", 
            obtenerNombreProveedor()
        );
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
