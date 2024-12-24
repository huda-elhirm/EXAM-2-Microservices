package com.example.examms.order;

import java.math.BigDecimal;
import java.util.List;

import com.example.examms.graphQL.ProductGraphQLClient;
import com.example.examms.product.PurchaseRequest;
import com.example.examms.product.PurchaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
//@RequiredArgsConstructor
public class OrderController {

  /*private final OrderService service;

  @PostMapping
  public ResponseEntity<Integer> createOrder(
      @RequestBody @Valid OrderRequest request
  ) {
    return ResponseEntity.ok(this.service.createOrder(request));
  }

  @GetMapping
  public ResponseEntity<List<OrderResponse>> findAll() {
    return ResponseEntity.ok(this.service.findAllOrders());
  }

  @GetMapping("/{order-id}")
  public ResponseEntity<OrderResponse> findById(
      @PathVariable("order-id") Integer orderId
  ) {
    return ResponseEntity.ok(this.service.findById(orderId));
  }*/



  private final ProductGraphQLClient productClient;

  public OrderController(ProductGraphQLClient productClient) {
    this.productClient = productClient;
  }

  @PostMapping
  public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
    for (PurchaseRequest purchaseRequest : orderRequest.products()) {
      // Récupérer les détails du produit via GraphQL
      PurchaseResponse product = productClient.getProductById(purchaseRequest.productId());

      // Vérifier la disponibilité du stock
      if (product.quantity() < purchaseRequest.quantity()) {
        return ResponseEntity.badRequest().body("Stock insuffisant pour le produit : " + product.name());
      }
    }

    // Logique pour sauvegarder la commande...
    return ResponseEntity.ok("Commande créée avec succès !");
  }
}
