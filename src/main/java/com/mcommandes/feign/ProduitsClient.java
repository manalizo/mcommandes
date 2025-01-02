package com.mcommandes.feign;

import com.mcommandes.dao.ProduitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "MPRODUITS")
public interface ProduitsClient {

    @GetMapping("/produits")
    List<ProduitDTO> getProduits();
}
