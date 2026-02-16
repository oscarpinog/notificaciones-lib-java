package com.notificacion.libreria;

import com.notificacion.libreria.aplicacion.puertos.entrada.NotificacionUseCase;
import com.notificacion.libreria.dominio.enums.TipoCanal;
import com.notificacion.libreria.dominio.excepciones.EnvioException;
import com.notificacion.libreria.dominio.excepciones.ValidacionException;
import com.notificacion.libreria.dominio.modelos.Notificacion;
import com.notificacion.libreria.dominio.modelos.ResultadoEnvio;
import com.notificacion.libreria.infraestructura.adaptadores.SlackAdapter;
import com.notificacion.libreria.infraestructura.facade.NotificacionesFacade;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase principal de demostración (Runner) para la librería de notificaciones.
 * Ilustra el flujo completo desde la inicialización de la fachada, la extensión
 * con nuevos canales, hasta el manejo robusto de excepciones.
 */
public class NotificacionesApplication {

    private static final Logger logger = LoggerFactory.getLogger(NotificacionesApplication.class);

    /**
     * Punto de entrada de la aplicación.
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        logger.info("Iniciando aplicación de ejemplo de Notificaciones...");

        // 1. Inicialización de la Fachada
        logger.debug("Configurando Fachada con credenciales iniciales");
        
        
     // 1. Configuración dinámica vía Docker / Entorno
        
        String canalEnv = System.getenv("NOTI_CANAL"); 
        TipoCanal canalSeleccionado;
        
     // Buscamos si el String existe dentro de los valores del Enum
        Optional<TipoCanal> canalOpt = Arrays.stream(TipoCanal.values())
            .filter(c -> c.name().equalsIgnoreCase(canalEnv))
            .findFirst();

        if (canalOpt.isEmpty()) {
            System.err.println("❌ Canal no registrado");
            return;
        }

        canalSeleccionado = canalOpt.get();

        logger.info("Configuración cargada (Canales: Email, SMS, Push)");
        NotificacionesFacade libreria = new NotificacionesFacade("NOTI_EMAIL_KEY", "NOTI_SMS_KEY", "NOTI_MOVILE_KEY");
        
        // 2. Extensibilidad: Registro de canal personalizado
        logger.debug("Probando extensibilidad: Agregando adaptador de Slack");
        libreria.registrarCanalPersonalizado(new SlackAdapter("https://hooks.slack.com/services/XYZ"));

        // 3. Obtención del puerto de entrada (Interface)
        NotificacionUseCase servicio = libreria.obtenerServicio();

        // 4. Intento de envío con Try-Catch (Manejo de Errores)
        try {
            System.out.println("--- Iniciando Proceso de Notificación ---");
            logger.info("Creando objeto Notificacion mediante Builder");

            Notificacion noti = new Notificacion.Builder()
                    .para("oscarRodriguez@test.com")
                    .conMensaje("Hola, este es un mensaje de NotificacionesOR")
                    .conAtributo("subject", "Subject,Gerardo Pino") // Requerido por nuestra validación de Email
                    .construir();

            // Ejecución y captura del "Result Type"
            logger.info("Enviando notificación ...");
            //ResultadoEnvio resultado = servicio.enviar(noti, TipoCanal.EMAIL);
            ResultadoEnvio resultado = servicio.enviar(noti, canalSeleccionado);

            // 5. Validación del resultado (Success Path)
            if (resultado.exitoso()) {
            	
            	System.out.println("||************************JSON-RESPONSE***********************||");
            	
            	System.out.printf("""
            		    {
            		      "exitoso": %b,
            		      "mensajeId": "%s",
            		      "detalle": "%s",
            		      "proveedor": "%s",
            		      "subject": "%s"
            		    }
            		    %n""", 
            		    resultado.exitoso(), 
            		    resultado.mensajeId(), 
            		    resultado.detalle(), 
            		    resultado.proveedor(),
            		    resultado.subject()
            		);
            	
            	
            	System.out.println("||*********************************************************||");
//                System.out.println("✅ ÉXITO: Mensaje enviado vía " + resultado.proveedor());
//                System.out.println("🆔 ID de Seguimiento: " + resultado.mensajeId());
//                System.out.println("📝 Detalle: " + resultado.detalle() );
//                System.out.println("||*********************************************************||");
//                System.out.println("||*********************************************************||");
                logger.info("Notificación procesada correctamente por el proveedor");
            }

        } catch (ValidacionException e) {
            // Captura errores de datos (ej: falta el subject, email sin @)
            logger.warn("Se detectó un error de validación en los datos de entrada");
            System.err.println("⚠️ ERROR DE VALIDACIÓN: " + e.getMessage());
            
        } catch (EnvioException e) {
            // Captura errores técnicos (ej: timeout del proveedor, API Key inválida)
            logger.error("Error técnico durante el envío: {}", e.getMessage());
            System.err.println("❌ ERROR DE ENVÍO: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("🔍 Causa raíz: " + e.getCause().getMessage());
            }
            
        } catch (Exception e) {
            // Captura cualquier otro error inesperado
            logger.error("Se produjo una excepción no controlada: ", e);
            System.err.println("🛑 ERROR INESPERADO: " + e.getMessage());
        } finally {
            System.out.println("--- Finalización del intento de envío ---");
            logger.info("Finalización del ciclo de ejecución del main");
        }
    }
}