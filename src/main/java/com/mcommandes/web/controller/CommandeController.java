package com.mcommandes.web.controller;

import com.mcommandes.configurations.ApplicationPropertiesConfiguration;
import com.mcommandes.model.Commande;
import com.mcommandes.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
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
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam("montant") double montant,
            @RequestParam("productid") int productid,
            @RequestParam("email") String email) {

        Commande commande = new Commande(description, quantite, date, montant, productid, email);
        Commande savedCommande = commandeService.saveCommande(commande);
        return ResponseEntity.ok(savedCommande);
    }

    @GetMapping
    public ResponseEntity<List<Commande>> getAllCommandes() {
        int commandesLast = appProperties.getLimitDeCommandes();
        System.out.println("commandesLast: " + commandesLast);

        LocalDate cutoffDate = LocalDate.now().minusDays(commandesLast);
        System.out.println("Cutoff date: " + cutoffDate);

        // Fetch all commandes
        List<Commande> commandes = commandeService.getAllCommandes();
        System.out.println("Initial commandes list size: " + (commandes != null ? commandes.size() : "null"));

        if (commandes != null) {
            commandes.forEach(commande -> System.out.println("Commande: " + commande));
        }

        // Filter commandes based on cutoff date
        List<Commande> filteredCommandes = commandes.stream()
                .filter(commande -> {
                    boolean result = isAfterCutoff(commande, cutoffDate);
                    System.out.println("Filtering Commande: " + commande + " Result: " + result);
                    return result;
                })
                .collect(Collectors.toList());
        System.out.println("Filtered commandes list size: " + filteredCommandes.size());

        // Return filtered commandes directly
        return ResponseEntity.ok(filteredCommandes);
    }

    private boolean isAfterCutoff(Commande commande, LocalDate cutoffDate) {
        LocalDate commandeDate = commande.getDate();
        System.out.println("Checking Commande Date: " + commandeDate + " against Cutoff: " + cutoffDate);
        return commandeDate != null && commandeDate.isAfter(cutoffDate);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable int id) {
        Optional<Commande> commande = commandeService.getCommandeById(id);
        return commande.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable int id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/delayedResponse")
    public String delayedResponse() throws InterruptedException {
        Thread.sleep(10000); // Simulate a delay of 10 seconds
        return "Response with delay";
    }

    @GetMapping("/user/{email}/commandes")
    public ResponseEntity<List<Commande>> getCommandesByUser(@PathVariable String email) {
        List<Commande> commandes = commandeService.getCommandeByEmail(email).orElse(null);
        if (commandes == null || commandes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(commandes);
    }


    @GetMapping("/details")
    public String getCommandeDetails() {
        return commandeService.getCommandeDetails();
    }

    @Override
    public Health health() {
        System.out.println("****** Actuator: CommandeController health() ");
        List<Commande> commandes = commandeService.getAllCommandes();

        if (commandes.isEmpty()) {
            return Health.down().build();
        }

        return Health.up().build();
    }

    // DTO to structure Commande responses
    class CommandeResponse {
        private int id;
        private String description;
        private int quantite;
        private LocalDate date;
        private double montant;
        private int productid;
        private String email;

        public CommandeResponse(int id, String description, int quantite, LocalDate date, double montant, int productid, String email) {
            this.id = id;
            this.description = description;
            this.quantite = quantite;
            this.date = date;
            this.montant = montant;
            this.productid = productid;
            this.email = email;
        }

        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public int getQuantite() { return quantite; }
        public void setQuantite(int quantite) { this.quantite = quantite; }

        public LocalDate getDate() { return date; }
        public void setDate(LocalDate date) { this.date = date; }

        public double getMontant() { return montant; }
        public void setMontant(double montant) { this.montant = montant; }

        public int getProductid() { return productid; }
        public void setProductid(int productid) { this.productid = productid; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}
