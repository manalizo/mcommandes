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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/create")
    public ResponseEntity<Commande> createCommande(
            @RequestParam("description") String description,
            @RequestParam("quantite") int quantite,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,  // Use LocalDate here
            @RequestParam("montant") double montant,
            @RequestParam("productid") int productid,
     @RequestParam("email") String email) {

        Commande commande = new Commande(description, quantite, date, montant, productid,email);
        Commande savedCommande = commandeService.saveCommande(commande);
        return ResponseEntity.ok(savedCommande);
    }


/*
    // Get all Commandes
    @GetMapping
    public ResponseEntity<List<Commande>> getAllCommandes() {

        List<Commande> commandes = commandeService.getAllCommandes();

        return ResponseEntity.ok( commandes);
    }*/



    @GetMapping
    public ResponseEntity<List<Commande>> getAllCommandes() {
       int commandesLast = appProperties.getLimitDeCommandes();
      //  int commandesLast=10;
        System.out.println("commandesLast: " + commandesLast);

        LocalDate cutoffDate = LocalDate.now().minusDays(commandesLast);
        System.out.println("Cutoff date: " + cutoffDate);

        List<Commande> commandes = commandeService.getAllCommandes();
        System.out.println("Initial commandes list size: " + (commandes != null ? commandes.size() : "null"));

        if (commandes != null) {
            commandes.forEach(commande -> System.out.println("Commande: " + commande));
        }

        List<Commande> filteredCommandes = commandes.stream()
                .filter(commande -> {
                    boolean result = isAfterCutoff(commande, cutoffDate);
                    System.out.println("Filtering Commande: " + commande + " Result: " + result);
                    return result;
                })
                .collect(Collectors.toList());

        System.out.println("Filtered commandes list size: " + filteredCommandes.size());

        return ResponseEntity.ok(filteredCommandes);
    }

    private boolean isAfterCutoff(Commande commande, LocalDate cutoffDate) {
        LocalDate commandeDate = commande.getDate();
        System.out.println("Checking Commande Date: " + commandeDate + " against Cutoff: " + cutoffDate);
        return commandeDate != null && commandeDate.isAfter(cutoffDate);
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

    @GetMapping("/delayedResponse")
    public String delayedResponse() throws InterruptedException {
        Thread.sleep(10000); // Délai de 10 secondes (simule une lenteur excessive)
        return "Réponse avec délai";
    }
    @GetMapping("/commande/email/")
    public ResponseEntity<List<Commande>> getCommandesByEmail(@RequestParam("email") String email) {
        Optional<List<Commande>> commandes = commandeService.getCommandeByEmail(email);
        return commandes.map(ResponseEntity::ok)  // If the list is present
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());  // If empty
    }
    @GetMapping("/details")
    public String getCommandeDetails() {
        return commandeService.getCommandeDetails();
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
