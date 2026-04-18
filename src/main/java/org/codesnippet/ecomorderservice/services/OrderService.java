package org.codesnippet.ecomorderservice.services;

import org.codesnippet.ecomorderservice.dto.Inventory;
import org.codesnippet.ecomorderservice.exceptions.MyCustomRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;

@Service
public class OrderService {

    private  final RestTemplate restTemplate;
    private final RestClient restClient;
    public OrderService(RestTemplate restTemplate, RestClient restClient) {
        this.restTemplate = restTemplate;
        this.restClient = restClient;
    }

    public String placeOrder(Long productId){
     /*
        // Rest Template Example
        String response = restTemplate.getForObject(
                "http://localhost:8081/inventory/" + productId,
                String.class
        );*/

/*
    //Simple RestClient Get
    ResponseEntity<Inventory> entity = restClient.get()
                .uri("http://localhost:8081/inventory/{productId}", productId)
                .retrieve()
                .toEntity(Inventory.class);
        */
        // onStatus Example
        ResponseEntity<Inventory> entity = restClient.get()
                .uri("http://localhost:8081/inventory/{productId}", productId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,((request, response) ->{
                    throw new MyCustomRuntimeException(response.getStatusCode(),response.getHeaders());
                } ))
                .toEntity(Inventory.class);

     /*
     // Exchange Example For Demo
     Object exchange = restClient.get()
                .uri("http://localhost:8081/inventory/{productId}", productId)
                .exchange(((clientRequest, clientResponse) ->
                {
                    if (clientResponse.getStatusCode().is4xxClientError()) {
                        throw new MyCustomRuntimeException(clientResponse.getStatusCode(), clientResponse.getHeaders());
                    } else {
                        return clientResponse.getBody();
                    }
                }));*/


        updateInventory(entity.getBody());

        return  entity.getBody()!=null && entity.getBody().getQuantity()>0?
              "Order Placed Successfully":
              "Product Out Of Stock";
    }

    private void updateInventory(Inventory inventory) {
        inventory.setQuantity(inventory.getQuantity()-1);
        restClient.post()
                .uri("http://localhost:8081/inventory")
                .body(inventory)
                .retrieve()
                .toBodilessEntity();
    }


}
