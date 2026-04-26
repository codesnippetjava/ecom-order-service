package org.codesnippet.ecomorderservice.client.config;

import feign.RequestTemplate;
import feign.codec.Encoder;
import org.codesnippet.ecomorderservice.dto.Inventory;
import tools.jackson.databind.ObjectMapper;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CustomInventoryEncoder implements Encoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) {

        try {
            // Cast to Inventory
            if (object instanceof Inventory inventory) {

                // Transform into required structure
                Map<String, Object> data = new HashMap<>();
                data.put("productId", inventory.getProductId());
                data.put("quantity", inventory.getQuantity());

                String json = objectMapper.writeValueAsString(data);

                template.body(json);
                template.header("Content-Type", "application/json");
            }

        } catch (Exception e) {
            throw new RuntimeException("Encoding failed", e);
        }
    }
}