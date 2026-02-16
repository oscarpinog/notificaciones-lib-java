package com.notificacion.libreria.infraestructura.adaptadores;


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
		
        return new ResultadoEnvio.Builder()
        .exitoso(true)
        .mensajeId("SLK-" + System.currentTimeMillis())
        .detalle("Mensaje enviado a Slack"+webhookUrl)
        .proveedor(obtenerNombreProveedor())
        .construir();
        
    }

    @Override public TipoCanal obtenerTipoCanal() { return TipoCanal.SLACK; }
    @Override public String obtenerNombreProveedor() { return "SlackAPI"; }
}
