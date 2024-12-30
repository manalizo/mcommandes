package com.mcommandes.configurations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class RestTemplate {


    /**
     * Configuration globale pour le bean RestTemplate.
     */
    @Configuration
    public class RestTemplateConfig {

        /**
         * Crée et configure un bean RestTemplate.
         *
         * @param builder Le RestTemplateBuilder pour personnaliser la configuration si nécessaire.
         * @return Une instance de RestTemplate.
         */
        @Bean
        public org.springframework.web.client.RestTemplate restTemplate(RestTemplateBuilder builder) {
            return builder.build();
        }
    }

}
