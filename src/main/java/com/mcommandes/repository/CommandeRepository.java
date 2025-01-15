package com.mcommandes.repository;

import com.mcommandes.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Integer> {
    List<Commande> findByEmail(String email);
}
