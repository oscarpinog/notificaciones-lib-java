package com.notificacion.libreria.infraestructura.adaptadores;

import com.notificacion.libreria.aplicacion.puertos.salida.EstrategiaEnvio;
import com.notificacion.libreria.dominio.enums.TipoCanal;
import com.notificacion.libreria.dominio.modelos.Notificacion;
import com.notificacion.libreria.dominio.modelos.ResultadoEnvio;
import java.util.UUID;

public class EmailAdapter implements EstrategiaEnvio {
    private final String apiKey;

    public EmailAdapter(String apiKey) { this.apiKey = apiKey; }

    @Override
    public ResultadoEnvio notificar(Notificacion notificacion) {
        // Simulación de lógica de Email (requiere subject)
        String subject = (String) notificacion.getAtributo("subject");
        if (subject == null) subject = "Sin Asunto";

        System.out.println("||*******************INICIO EMAIL ADAPTER************************||");
        
        return new ResultadoEnvio.Builder()
        .exitoso(true)
        .mensajeId(UUID.randomUUID().toString())
        .detalle(String.format("Email enviado a "+ notificacion.getReceptor())+" key: "+apiKey)
        .proveedor(obtenerNombreProveedor())
        .subject(subject) //Solo EMAIL tiene subject
        .construir();
        
    }

    @Override public TipoCanal obtenerTipoCanal() { return TipoCanal.EMAIL; }
    @Override public String obtenerNombreProveedor() { return "EMAIL-OR"; }
}
