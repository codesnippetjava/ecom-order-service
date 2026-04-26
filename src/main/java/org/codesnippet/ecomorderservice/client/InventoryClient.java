package org.codesnippet.ecomorderservice.client;

import org.codesnippet.ecomorderservice.client.config.InventoryFeignClientConfig;
import org.codesnippet.ecomorderservice.dto.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service",
        url = "http://localhost:8081",
        configuration = InventoryFeignClientConfig.class
)
public interface InventoryClient {
    @GetMapping("/inventory/{productId}")
    Inventory getInventory(@PathVariable Long productId);

    @PostMapping("/inventory")
    String updateInventory(@RequestBody Inventory inventory);
}
