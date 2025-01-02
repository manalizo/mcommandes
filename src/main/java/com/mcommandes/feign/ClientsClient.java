package com.mcommandes.feign;

import com.mcommandes.dao.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "MCLIENTS")
public interface ClientsClient {
    @GetMapping("/clients")
    List<ClientDTO> getClients();
}
