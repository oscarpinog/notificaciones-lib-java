package com.notificacion.libreria.dominio.modelos;

public record ResultadoEnvio(
	    boolean exitoso, 
	    String mensajeId, 
	    String detalle, 
	    String proveedor,
	    String subject
	) {
	    // Clase Builder interna
	    public static class Builder {
	        private boolean exitoso;
	        private String mensajeId;
	        private String detalle;
	        private String proveedor;
	        private String subject;

	        public Builder exitoso(boolean exitoso) {
	            this.exitoso = exitoso;
	            return this;
	        }

	        public Builder mensajeId(String mensajeId) {
	            this.mensajeId = mensajeId;
	            return this;
	        }

	        public Builder detalle(String detalle) {
	            this.detalle = detalle;
	            return this;
	        }

	        public Builder proveedor(String proveedor) {
	            this.proveedor = proveedor;
	            return this;
	        }

	        public Builder subject(String subject) {
	            this.subject = subject;
	            return this;
	        }

	        public ResultadoEnvio construir() {
	            return new ResultadoEnvio(exitoso, mensajeId, detalle, proveedor, subject);
	        }
	    }
	}