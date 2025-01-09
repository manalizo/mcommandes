package com.mcommandes.web.controller;

import com.mcommandes.configurations.ApplicationPropertiesConfiguration;
import com.mcommandes.dao.ClientDTO;
import com.mcommandes.dao.ProduitDTO;
import com.mcommandes.feign.ClientsClient;
import com.mcommandes.feign.ProduitsClient;
import com.mcommandes.model.Commande;
import com.mcommandes.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/commandes")

@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class CommandeController implements HealthIndicator {

        @Autowired
        private CommandeService commandeService;
        @Autowired
        ApplicationPropertiesConfiguration appProperties;

        private int commandesLast;

        // Create or Update a Commande
        @PostMapping("/create")
        public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) {
            Commande savedCommande = commandeService.saveCommande(commande);
            return ResponseEntity.ok(savedCommande);
        }


    // Get all Commandes
        @GetMapping
        public ResponseEntity<List<Commande>> getAllCommandes() {
            commandesLast=appProperties.getLimitDeCommandes();
            LocalDate cutoffDate = LocalDate.now().minusDays(commandesLast);

            List<Commande> commandes = commandeService.getAllCommandes();

            return ResponseEntity.ok( commandes.stream()
                    .filter(commande -> {
                        if (commande.getDate() == null) {
                            return false;
                        }
                        LocalDate commandeDate = commande.getDate().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        return commandeDate.isAfter(cutoffDate);
                    })
                    .collect(Collectors.toList()));
        }

        // Get a Commande by ID
        @GetMapping("/{id}")
        public ResponseEntity<Commande> getCommandeById(@PathVariable int id) {
            Optional<Commande> commande = commandeService.getCommandeById(id);
            return commande.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        // Delete a Commande
        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Void> deleteCommande(@PathVariable int id) {
            commandeService.deleteCommande(id);
            return ResponseEntity.noContent().build();
        }

    @Override
    public Health health() {
        System.out.println("****** Actuator : ProductController health() ");
        List<Commande> commandes = commandeService.getAllCommandes();

        if (commandes.isEmpty()) {
            return Health.down().build();
        }

        return Health.up().build();
    }

/*
    private final ClientsClient clientsClient;
    private final ProduitsClient produitsClient;

    public CommandeController(ClientsClient clientsClient, ProduitsClient produitsClient) {
        this.clientsClient = clientsClient;
        this.produitsClient = produitsClient;
    }

    @GetMapping("/details")
    public Map<String, Object> getCommandeDetails() {
        List<ClientDTO> clients = clientsClient.getClients();
        List<ProduitDTO> produits = produitsClient.getProduits();
        return Map.of("clients", clients, "produits", produits);
    }*/
}
