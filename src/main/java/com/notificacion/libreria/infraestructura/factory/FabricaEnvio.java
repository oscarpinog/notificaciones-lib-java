package com.notificacion.libreria.infraestructura.factory;

import com.notificacion.libreria.aplicacion.puertos.salida.EstrategiaEnvio;
import com.notificacion.libreria.dominio.enums.TipoCanal;
import java.util.EnumMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fábrica encargada de gestionar y proveer las diferentes estrategias de envío.
 * Utiliza el patrón Registry para mantener un mapa de adaptadores disponibles,
 * permitiendo la selección dinámica del canal en tiempo de ejecución.
 */
public class FabricaEnvio {

    private static final Logger logger = LoggerFactory.getLogger(FabricaEnvio.class);
    private final Map<TipoCanal, EstrategiaEnvio> estrategias = new EnumMap<>(TipoCanal.class);

    /**
     * Registra una nueva estrategia de envío en el mapa. 
     * Si el canal ya existe, será sobrescrito con la nueva implementación.
     * * @param estrategia Instancia del adaptador que implementa {@link EstrategiaEnvio}.
     */
    public void registrarEstrategia(EstrategiaEnvio estrategia) {
        logger.debug("Registrando estrategia para el canal: {}", estrategia.obtenerTipoCanal());
        estrategias.put(estrategia.obtenerTipoCanal(), estrategia);
    }

    /**
     * Recupera la estrategia de envío asociada a un tipo de canal específico.
     * * @param canal El tipo de canal deseado (EMAIL, SMS, etc.).
     * @return La instancia de {@link EstrategiaEnvio} configurada.
     * @throws IllegalArgumentException Si no se ha registrado ningún adaptador para el canal solicitado.
     */
    public EstrategiaEnvio obtenerEstrategia(TipoCanal canal) {
        EstrategiaEnvio estrategia = estrategias.get(canal);
        if (estrategia == null) {
            logger.error("Error de configuración: No existe un adaptador registrado para el canal {}", canal);
            throw new IllegalArgumentException("No hay proveedor configurado para el canal: " + canal);
        }
        logger.trace("Estrategia recuperada con éxito para el canal {}", canal);
        return estrategia;
    }
}
