package com.mcommandes;

import com.mcommandes.service.CommandeService;
import com.mcommandes.web.controller.CommandeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class McommandesApplication {

	public static void main(String[] args) {
		SpringApplication.run(McommandesApplication.class, args);
	}

	@Bean
	public HealthIndicator commandesHealthIndicator(CommandeService commande) {
		return () -> {
			boolean hasCommandes = !commande.getAllCommandes().isEmpty();
			return hasCommandes ? Health.up().build() : Health.down().withDetail("reason", "No commandes available").build();
		};
	}
}
