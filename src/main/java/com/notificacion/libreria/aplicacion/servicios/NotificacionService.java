package com.notificacion.libreria.aplicacion.servicios;

import com.notificacion.libreria.aplicacion.puertos.entrada.NotificacionUseCase;
import com.notificacion.libreria.aplicacion.puertos.salida.EstrategiaEnvio;
import com.notificacion.libreria.dominio.enums.TipoCanal;
import com.notificacion.libreria.dominio.excepciones.EnvioException;
import com.notificacion.libreria.dominio.excepciones.ValidacionException;
import com.notificacion.libreria.dominio.modelos.Notificacion;
import com.notificacion.libreria.dominio.modelos.ResultadoEnvio;
import com.notificacion.libreria.infraestructura.factory.FabricaEnvio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementación principal del caso de uso para el envío de notificaciones.
 * Actúa como el orquestador de la lógica de negocio, validando la información
 * y delegando el envío técnico a la estrategia correspondiente.
 * * @author TuNombre
 * @version 1.0
 */
public class NotificacionService implements NotificacionUseCase {

    private static final Logger logger = LoggerFactory.getLogger(NotificacionService.class);
    private final FabricaEnvio fabrica;

    /**
     * Construye el servicio con su fábrica de estrategias.
     * * @param fabrica Componente encargado de proveer los adaptadores de envío.
     */
    public NotificacionService(FabricaEnvio fabrica) {
        this.fabrica = fabrica;
    }

    /**
     * Procesa y envía una notificación a través de un canal específico.
     * * @param notificacion Objeto con los datos del receptor, mensaje y atributos extra.
     * @param canal        Tipo de canal (EMAIL, SMS, PUSH, SLACK) a utilizar.
     * @return {@link ResultadoEnvio} con el estado de la operación y metadatos del proveedor.
     * @throws ValidacionException Si los datos de la notificación no cumplen con las reglas de negocio.
     * @throws EnvioException      Si ocurre un error técnico irrecuperable en el proveedor de servicios.
     */
    @Override
    public ResultadoEnvio enviar(Notificacion notificacion, TipoCanal canal) {
        logger.info("Iniciando solicitud de envío. Canal: {}, Receptor: {}", canal, notificacion.getReceptor());

        try {
            // A. VALIDACIÓN
            validarNotificacion(notificacion, canal);
            logger.debug("Validación de datos exitosa para el canal {}", canal);

            // B. OBTENCIÓN DE ESTRATEGIA
            EstrategiaEnvio estrategia = fabrica.obtenerEstrategia(canal);
            logger.debug("Estrategia seleccionada: {}", estrategia.getClass().getSimpleName());

            // C. ENVÍO
            ResultadoEnvio resultado = estrategia.notificar(notificacion);
            
            if (resultado.exitoso()) {
                logger.info("Notificación enviada con éxito. ID Seguimiento: {}", resultado.mensajeId());
            } else {
                logger.warn("El envío finalizó pero el proveedor reportó novedades: {}", resultado.detalle());
            }

            return resultado;

        } catch (ValidacionException | IllegalArgumentException e) {
            logger.error("Fallo en la validación de la solicitud: {}", e.getMessage());
            throw new ValidacionException("Error de datos: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error técnico crítico al intentar enviar por {}. Causa: {}", canal, e.getMessage(), e);
            throw new EnvioException("Fallo crítico al enviar por " + canal, e);
        }
    }

    /**
     * Realiza las comprobaciones de integridad de la notificación según el canal.
     * * @param n     Notificación a validar.
     * @param canal Canal para aplicar reglas específicas (ej. validación de subject en Email).
     * @throws ValidacionException Si alguna regla de negocio es violada.
     */
    private void validarNotificacion(Notificacion n, TipoCanal canal) {
        if (n.getReceptor() == null || n.getReceptor().isBlank()) {
            throw new ValidacionException("El receptor no puede estar vacío");
        }
        
        if (canal == TipoCanal.EMAIL) {
            if (!n.getReceptor().contains("@")) {
                throw new ValidacionException("Formato de email inválido");
            }
            if (n.getAtributo("subject") == null) {
                throw new ValidacionException("El canal EMAIL requiere un atributo 'subject'.");
            }
        }
        // Logs de depuración para auditoría interna
        logger.trace("Fin de la validación privada para el receptor: {}", n.getReceptor());
    }
}