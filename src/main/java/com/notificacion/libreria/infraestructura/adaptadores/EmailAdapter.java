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
        
        System.out.println("[Email] Enviando via EMAIL con API Key: " + apiKey);
        return new ResultadoEnvio(
        	    true, 
        	    UUID.randomUUID().toString(), 
        	    String.format("Email enviado a %s \nSubject: %s", notificacion.getReceptor(), subject), 
        	    obtenerNombreProveedor()
        	);
        
    }

    @Override public TipoCanal obtenerTipoCanal() { return TipoCanal.EMAIL; }
    @Override public String obtenerNombreProveedor() { return "EMAIL-OR"; }
}
