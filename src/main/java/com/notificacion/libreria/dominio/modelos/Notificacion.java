package com.notificacion.libreria.dominio.modelos;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Representa la entidad de notificación en el dominio.
 * Objeto inmutable que contiene la información necesaria para realizar un envío,
 * incluyendo el receptor, el contenido y atributos adicionales dinámicos.
 */
public class Notificacion {

    private final String receptor;
    private final String contenido;
    private final Map<String, Object> atributos;

    /**
     * Constructor privado que garantiza la inmutabilidad de la clase.
     * @param builder Instancia del Builder con los datos cargados.
     */
    private Notificacion(Builder builder) {
        this.receptor = builder.receptor;
        this.contenido = builder.contenido;
        this.atributos = Collections.unmodifiableMap(builder.atributos);
    }

    /** @return Identificador del destinatario. */
    public String getReceptor() { return receptor; }

    /** @return Cuerpo del mensaje. */
    public String getContenido() { return contenido; }

    /**
     * Recupera un metadato adicional del mapa de atributos.
     * @param clave Nombre del atributo.
     * @return Valor del atributo u null si no existe.
     */
    public Object getAtributo(String clave) { return atributos.get(clave); }

    /**
     * Implementación del patrón Builder para la creación de objetos {@link Notificacion}.
     * Incluye validaciones de estado para asegurar la integridad del objeto creado.
     */
    public static class Builder {
        private static final Logger builderLogger = LoggerFactory.getLogger(Builder.class);
        
        private String receptor;
        private String contenido;
        private final Map<String, Object> atributos = new HashMap<>();

        /**
         * Asigna el receptor de la notificación.
         * @param receptor Email, teléfono o identificador.
         * @return Instancia actual del Builder.
         */
        public Builder para(String receptor) {
            this.receptor = receptor;
            return this;
        }

        /**
         * Asigna el contenido del mensaje.
         * @param contenido Cuerpo del mensaje.
         * @return Instancia actual del Builder.
         */
        public Builder conMensaje(String contenido) {
            this.contenido = contenido;
            return this;
        }

        /**
         * Agrega un atributo dinámico requerido por adaptadores específicos.
         * @param clave Nombre del atributo (ej: "subject").
         * @param valor Valor del atributo (puede ser cualquier Objeto).
         * @return Instancia actual del Builder.
         */
        public Builder conAtributo(String clave, Object valor) {
            this.atributos.put(clave, valor);
            return this;
        }

        /**
         * Valida y construye la instancia de {@link Notificacion}.
         * @return Un nuevo objeto Notificacion.
         * @throws IllegalStateException Si el receptor o el contenido son nulos o están vacíos.
         */
        public Notificacion construir() {
            if (receptor == null || receptor.isBlank()) {
                builderLogger.error("Error al construir Notificacion: Receptor ausente.");
                throw new IllegalStateException("Receptor es obligatorio");
            }
            if (contenido == null || contenido.isBlank()) {
                builderLogger.error("Error al construir Notificacion: Contenido ausente.");
                throw new IllegalStateException("Contenido es obligatorio");
            }

            builderLogger.trace("Notificacion construida exitosamente para el receptor: {}", receptor);
            return new Notificacion(this);
        }
    }
}