package com.notificacion.libreria.infraestructura.adaptadores;

import com.notificacion.libreria.aplicacion.puertos.salida.EstrategiaEnvio;
import com.notificacion.libreria.dominio.enums.TipoCanal;
import com.notificacion.libreria.dominio.modelos.Notificacion;
import com.notificacion.libreria.dominio.modelos.ResultadoEnvio;
import java.util.UUID;

public class PushAdapter implements EstrategiaEnvio {
    private final String firebaseConfigPath;

    public PushAdapter(String firebaseConfigPath) {
        this.firebaseConfigPath = firebaseConfigPath;
    }

    @Override
    public ResultadoEnvio notificar(Notificacion notificacion) {
        // Las notificaciones push suelen requerir un "dispositivo" o "token"
        // que en este caso viaja en el campo 'receptor'
    	System.out.println("||*******************INICIO PUSH ADAPTER************************||");

        return new ResultadoEnvio.Builder()
        .exitoso(true)
        .mensajeId(UUID.randomUUID().toString())
        .detalle("Notificación Push enviada al servicio de mensajería Movile: "+firebaseConfigPath)
        .proveedor(obtenerNombreProveedor())
        .construir();
        

    }

    @Override
    public TipoCanal obtenerTipoCanal() {
        return TipoCanal.PUSH;
    }

    @Override
    public String obtenerNombreProveedor() {
        return "Movile_Proveedor";
    }
}
