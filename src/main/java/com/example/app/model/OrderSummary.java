package com.example.app.model;

public record OrderSummary(
        double subtotal,
        double discountAmount,
        double taxableAmount,
        double taxAmount,
        double shippingCost,
        double totalAmount
) {
}
