package com.notificacion.libreria.dominio.modelos;

public record ResultadoEnvio(
	    boolean exitoso, 
	    String mensajeId, 
	    String detalle, 
	    String proveedor
	) {}
