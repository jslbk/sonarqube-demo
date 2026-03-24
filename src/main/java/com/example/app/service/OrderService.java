package com.example.app.service;

import com.example.app.model.CustomerType;
import com.example.app.model.OrderSummary;

public class OrderService {

    private final PriceCalculator priceCalculator;
    private final ShippingCalculator shippingCalculator;

    public OrderService(PriceCalculator priceCalculator, ShippingCalculator shippingCalculator) {
        this.priceCalculator = priceCalculator;
        this.shippingCalculator = shippingCalculator;
    }

    public OrderSummary checkout(double subtotal, CustomerType customerType) {
        double discount = priceCalculator.calculateDiscount(subtotal, customerType);
        double taxableAmount = round(subtotal - discount);
        double tax = priceCalculator.calculateTax(taxableAmount);
        double shipping = shippingCalculator.calculateShipping(taxableAmount);
        double total = round(taxableAmount + tax + shipping);

        return new OrderSummary(subtotal, discount, taxableAmount, tax, shipping, total);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
