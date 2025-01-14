package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración global para el manejo de CORS (Cross-Origin Resource Sharing).
 *
 * <p>
 * Esta clase define una configuración que permite a la aplicación aceptar solicitudes
 * de diferentes orígenes y métodos HTTP, facilitando la interacción con aplicaciones
 * cliente en diferentes dominios.
 * </p>
 */
@Configuration
public class CorsConfig {

    /**
     * Configura las reglas de CORS para la aplicación.
     *
     * <p>
     * Este método define un bean de tipo {@link WebMvcConfigurer} que registra
     * las reglas de CORS aplicables a todas las rutas de la aplicación.
     * Se permiten todas las rutas (`/**`), cualquier origen (`*`), y los
     * métodos HTTP `GET`, `POST`, `PUT` y `DELETE`.
     * </p>
     *
     * @return Un configurador de MVC con las reglas de CORS definidas.
     */
    @Bean
    public WebMvcConfigurer CORSConfigurer(){
        return new WebMvcConfigurer() {

            /**
             * Configura las reglas de CORS.
             *
             * <p>
             * Permite que cualquier origen acceda a las rutas de la aplicación
             * con los métodos `GET`, `POST`, `PUT` y `DELETE`.
             * </p>
             *
             * @param registry El registro donde se definen las reglas de CORS.
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permite todas las rutas
                        .allowedOriginPatterns("http://localhost:4200") // Permite el origen de tu frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                        .allowedHeaders("Authorization", "Content-Type", "Accept") // Permite todos los encabezados
                        .exposedHeaders("Authorization") // Expone encabezados específicos
                        .allowCredentials(true);
            }
        };
    }

}
