package com.mcommandes.web.controller;

import com.mcommandes.dao.ClientDTO;
import com.mcommandes.dao.ProduitDTO;
import com.mcommandes.feign.ClientsClient;
import com.mcommandes.feign.ProduitsClient;
import com.mcommandes.model.Commande;
import com.mcommandes.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/commandes")
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class CommandeController {

        @Autowired
        private CommandeService commandeService;

        // Create or Update a Commande
        @PostMapping("/create")
        public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) {
            Commande savedCommande = commandeService.saveCommande(commande);
            return ResponseEntity.ok(savedCommande);
        }

        // Get all Commandes
        @GetMapping
        public ResponseEntity<List<Commande>> getAllCommandes() {
            List<Commande> commandes = commandeService.getAllCommandes();
            return ResponseEntity.ok(commandes);
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
