package com.mcommandes.service;

import com.mcommandes.model.Commande;
import com.mcommandes.repository.CommandeRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private RestTemplate restTemplate; // Inject RestTemplate

    // Create or Update
    public Commande saveCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    // Get all commandes
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    // Get a specific commande by ID
    public Optional<Commande> getCommandeById(int id) {
        return commandeRepository.findById(id);
    }

    // Delete a commande
    public void deleteCommande(int id) {
        commandeRepository.deleteById(id);
    }

    // Integration with another microservice (e.g., delayedResponse endpoint)
    @CircuitBreaker(name = "commandeService", fallbackMethod = "defaultResponse") // Resilience4j Circuit Breaker
    public String getCommandeDetails() {
        // URL of the target microservice
        String url = "http://localhost:8082/commandes/delayedResponse";
        return restTemplate.getForObject(url, String.class);
    }

    // Fallback method in case of failure
    public String defaultResponse(Throwable throwable) {
        return "Service indisponible, réponse de secours activée.";
    }
}
