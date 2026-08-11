package org.codesnippet.ecomorderservice.services;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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
    @Bulkhead(name = "inventoryThreadPool",
            type = Bulkhead.Type.THREADPOOL,
            fallbackMethod = "bulkHeadFallbackMethod")
    public CompletableFuture<Inventory> getInventory(Long productId) {

        System.out.println(
                "Calling Inventory Service: "
                        + productId
                        + " | Thread: "
                        + Thread.currentThread().getName()
        );

        return CompletableFuture.completedFuture(
                inventoryClient.getInventory(productId)
        );
    }

    public CompletableFuture<Inventory> bulkHeadFallbackMethod(
            Long productId,
            Throwable throwable) {

        System.out.println(
                "Bulkhead fallback: "
                        + productId
                        + " | Exception: "
                        + throwable.getClass().getSimpleName()
        );

        return CompletableFuture.completedFuture(
                new Inventory(productId.toString(), 0)
        );
    }
}