package com.example.examms.graphQL;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import com.example.examms.product.PurchaseResponse;

import java.math.BigDecimal;

@Service
public class ProductGraphQLClient {

    private final WebClient webClient;

    public ProductGraphQLClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081/graphql").build(); // URL du microservice Product
    }

    public PurchaseResponse getProductById(Integer productId) {
        String query = """
            query {
                getProductById(id: "%s") {
                    id
                    name
                    description
                    price
                    stock
                }
            }
        """.formatted(productId);

        Map<String, Object> response = webClient.post()
                .bodyValue(Map.of("query", query))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        if (response == null || !response.containsKey("data")) {
            throw new RuntimeException("Erreur : Réponse vide ou invalide depuis l'API GraphQL");
        }

        Map<String, Object> productData = (Map<String, Object>) ((Map<String, Object>) response.get("data")).get("getProductById");
        if (productData == null) {
            throw new RuntimeException("Produit non trouvé pour l'ID : " + productId);
        }

        return new PurchaseResponse(
                Integer.parseInt(productData.get("id").toString()),
                productData.get("name").toString(),
                productData.get("description").toString(),
                new BigDecimal(productData.get("price").toString()),
                Double.parseDouble(productData.get("stock").toString())
        );
    }
}

