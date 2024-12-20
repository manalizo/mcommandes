package com.mcommandes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeDao extends JpaRepository<com.mcommandes.model.Commande, Long> {
}
