package com.example.examms.kafka;

import com.example.examms.customer.CustomerResponse;
import com.example.examms.order.PaymentMethod;
import com.example.examms.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation (
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products

) {
}
