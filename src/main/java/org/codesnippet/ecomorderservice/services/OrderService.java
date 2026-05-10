package org.codesnippet.ecomorderservice.services;

import com.netflix.appinfo.InstanceInfo;
import org.codesnippet.ecomorderservice.client.InventoryClient;
import org.codesnippet.ecomorderservice.dto.Inventory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class OrderService {
    private final InventoryClient inventoryClient;
    private  final RestTemplate restTemplate;
    private final RestClient restClient;
    private final DiscoveryClient discoveryClient;

    public OrderService(InventoryClient inventoryClient, RestTemplate restTemplate, RestClient restClient, DiscoveryClient discoveryClient) {
        this.inventoryClient = inventoryClient;
        this.restTemplate = restTemplate;
        this.restClient = restClient;
        this.discoveryClient = discoveryClient;
    }

    public String placeOrder(Long productId){

     /*
        // Rest Template Example
        String response = restTemplate.getForObject(
                "http://localhost:8081/inventory/" + productId,
                String.class
        );*/

    /*ResponseEntity<Inventory> entity = restClient.get()
                .uri("http://localhost:8081/inventory/{productId}", productId)
                .retrieve()
                .toEntity(Inventory.class);
*/
        Inventory inventory = inventoryClient.getInventory(productId);
        int quantity = inventory.getQuantity();
        updateInventory(inventory);

        return  quantity>0?
              "Order Placed Successfully":
              "Product Out Of Stock";
    }

    private void updateInventory(Inventory inventory) {
        inventory.setQuantity(inventory.getQuantity()-1);
        inventoryClient.updateInventory(inventory);
        /*restClient.post()
                .uri("http://localhost:8081/inventory")
                .body(inventory)
                .retrieve()
                .toBodilessEntity();*/
    }


}
