package com.example.examms.graphQL;

import com.example.examms.product.PurchaseResponse; // Ensure this class is correctly defined
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GraphQLClient {

    private final RestTemplate restTemplate;

    public GraphQLClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PurchaseResponse fetchProduct(String productId) {
        // Construct the GraphQL query
        String query = "{ product(id: \"" + productId + "\") { id name price } }";

        // Create the request body
        String requestBody = "{ \"query\": \"" + query + "\" }";

        // Set up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP entity
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Make the request to the GraphQL endpoint
        ResponseEntity<PurchaseResponse> response = restTemplate.exchange(
                "http://product-service/graphql",
                HttpMethod.POST,
                entity,
                PurchaseResponse.class
        );

        // Check for a successful response
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody(); // Return the entire response
        } else {
            // Handle error response
            throw new RuntimeException("Failed to fetch product: " + response.getStatusCode());
        }
    }
}