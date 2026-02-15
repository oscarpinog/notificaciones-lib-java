package com.notificacion.libreria.infraestructura.facade;

import com.notificacion.libreria.aplicacion.puertos.entrada.NotificacionUseCase;
import com.notificacion.libreria.aplicacion.puertos.salida.EstrategiaEnvio;
import com.notificacion.libreria.aplicacion.servicios.NotificacionService;
import com.notificacion.libreria.infraestructura.adaptadores.EmailAdapter;
import com.notificacion.libreria.infraestructura.adaptadores.PushAdapter;
import com.notificacion.libreria.infraestructura.adaptadores.SmsAdapter;
import com.notificacion.libreria.infraestructura.factory.FabricaEnvio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fachada principal de la librería de notificaciones.
 * Proporciona un punto de entrada simplificado para configurar la librería,
 * obtener el servicio de envío y registrar nuevos canales de comunicación.
 */
public class NotificacionesFacade {

    private static final Logger logger = LoggerFactory.getLogger(NotificacionesFacade.class);
    
    // La fábrica se vuelve un atributo para poder agregarle canales luego
    private final FabricaEnvio fabrica;
    private final NotificacionService servicio;

    /**
     * Inicializa la fachada configurando los adaptadores por defecto (Email, SMS y Push).
     * * @param emailKey Clave de API para el servicio de Email.
     * @param smsId    Identificador para el servicio de SMS.
     * @param pushPath Ruta al archivo de configuración para notificaciones Push.
     */
    public NotificacionesFacade(String emailKey, String smsId, String pushPath) {
        logger.info("Inicializando NotificacionesFacade...");
        this.fabrica = new FabricaEnvio();
        
        // Registro inicial
        logger.debug("Registrando adaptadores por defecto: Email, SMS, Push");
        this.fabrica.registrarEstrategia(new EmailAdapter(emailKey));
        this.fabrica.registrarEstrategia(new SmsAdapter(smsId));
        this.fabrica.registrarEstrategia(new PushAdapter(pushPath));
        
        this.servicio = new NotificacionService(this.fabrica);
        logger.info("Fachada inicializada y servicio de notificaciones listo.");
    }

    /**
     * Devuelve el caso de uso para enviar notificaciones.
     * * @return Una instancia de {@link NotificacionUseCase}.
     */
    public NotificacionUseCase obtenerServicio() {
        return this.servicio;
    }

    /**
     * Permite al usuario de la librería extenderla con nuevos canales (SOLID: Open/Closed).
     * * @param nuevaEstrategia Implementación de un nuevo canal de envío.
     */
    public void registrarCanalPersonalizado(EstrategiaEnvio nuevaEstrategia) {
        if (nuevaEstrategia != null) {
            logger.info("Registrando canal personalizado: {}", nuevaEstrategia.obtenerTipoCanal());
            this.fabrica.registrarEstrategia(nuevaEstrategia);
        } else {
            logger.warn("Se intentó registrar una estrategia nula.");
        }
    }
}