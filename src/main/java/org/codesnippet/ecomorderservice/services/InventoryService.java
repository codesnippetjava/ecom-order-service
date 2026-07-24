package org.codesnippet.ecomorderservice.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.codesnippet.ecomorderservice.client.InventoryClient;
import org.codesnippet.ecomorderservice.dto.Inventory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class InventoryService {

    private final InventoryClient inventoryClient;

    public InventoryService(InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

   /* @Retryable(
            retryFor = RuntimeException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    @RateLimiter(name = "inventoryService",fallbackMethod = "fallbackMethod" )


    @CircuitBreaker(name = "inventoryServiceCircuitBreaker", fallbackMethod = "circuitBreakerFallbackMethod")
   */
    @TimeLimiter(name = "inventoryServiceTimeLimiter", fallbackMethod = "timeLimiterFallbackMethod")
   public CompletableFuture<Inventory> getInventory(Long productId) {

       System.out.println("Calling Inventory Service for productId: " + productId);
       return CompletableFuture.supplyAsync(()
               -> inventoryClient.getInventory(productId));
    }
    public CompletableFuture<Inventory> timeLimiterFallbackMethod(Long productId, Throwable throwable){
        System.out.println("Fallback Method Called for productId: " + productId);
        Inventory inventory = new Inventory(productId.toString(), 0);
        return CompletableFuture.completedFuture(inventory);
    }



}
