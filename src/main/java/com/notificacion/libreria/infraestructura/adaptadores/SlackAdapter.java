package com.notificacion.libreria.infraestructura.adaptadores;

import java.util.UUID;

import com.notificacion.libreria.aplicacion.puertos.salida.EstrategiaEnvio;
import com.notificacion.libreria.dominio.enums.TipoCanal;
import com.notificacion.libreria.dominio.modelos.Notificacion;
import com.notificacion.libreria.dominio.modelos.ResultadoEnvio;

public class SlackAdapter implements EstrategiaEnvio {
    private final String webhookUrl;

    public SlackAdapter(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    @Override
    public ResultadoEnvio notificar(Notificacion notificacion) {
    	System.out.println("||*******************INICIO SLACK ADAPTER************************||");
        System.out.println("[Slack] Enviando mensaje al Webhook: " + webhookUrl);
        System.out.println("Slack - Registrado bajo extensibilidad.");
		
        return new ResultadoEnvio.Builder()
        .exitoso(true)
        .mensajeId("SLK-" + System.currentTimeMillis())
        .detalle("Mensaje enviado a Slack")
        .proveedor(obtenerNombreProveedor())
        .construir();
        
    }

    @Override public TipoCanal obtenerTipoCanal() { return TipoCanal.SLACK; }
    @Override public String obtenerNombreProveedor() { return "SlackAPI"; }
}
