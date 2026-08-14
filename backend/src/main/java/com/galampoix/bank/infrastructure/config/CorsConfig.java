package com.galampoix.bank.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration CORS de l'API REST.
 * <p>
 * Autorise les origines connues du frontend (développement local et
 * déploiement de production) à appeler les endpoints exposés sous
 * {@code /api/**}.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Déclare les règles CORS applicables à l'ensemble des endpoints de l'API.
     *
     * @param registry registre Spring MVC dans lequel enregistrer les règles CORS
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173", "https://java-bank-frontend.onrender.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
