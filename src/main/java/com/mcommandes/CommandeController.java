package com.mcommandes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeRepository commandeRepository;

    // POST /commandes : Créer une commande
    @PostMapping
    public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) {
        Commande savedCommande = commandeRepository.save(commande);
        return new ResponseEntity<>(savedCommande, HttpStatus.CREATED);
    }

    // GET /commandes : Récupérer toutes les commandes
    @GetMapping
    public ResponseEntity<List<Commande>> getAllCommandes() {
        List<Commande> commandes = commandeRepository.findAll();
        return new ResponseEntity<>(commandes, HttpStatus.OK);
    }

    // GET /commandes/{id} : Récupérer une commande par ID
    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable Long id) {
        return commandeRepository.findById(id)
                .map(commande -> new ResponseEntity<>(commande, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // PUT /commandes/{id} : Mettre à jour une commande
    @PutMapping("/{id}")
    public ResponseEntity<Commande> updateCommande(@PathVariable Long id, @RequestBody Commande updatedCommande) {
        return commandeRepository.findById(id)
                .map(commande -> {
                    commande.setDescription(updatedCommande.getDescription());
                    commande.setQuantite(updatedCommande.getQuantite());
                    commande.setDate(updatedCommande.getDate());
                    commande.setMontant(updatedCommande.getMontant());
                    Commande savedCommande = commandeRepository.save(commande);
                    return new ResponseEntity<>(savedCommande, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // DELETE /commandes/{id} : Supprimer une commande
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        return commandeRepository.findById(id)
                .map(commande -> {
                    commandeRepository.delete(commande);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
