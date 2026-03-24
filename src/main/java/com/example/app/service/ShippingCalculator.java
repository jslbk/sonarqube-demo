package com.example.app.service;

import com.example.app.config.AppConfig;

public class ShippingCalculator {

    private final AppConfig config;

    public ShippingCalculator(AppConfig config) {
        this.config = config;
    }

    public double calculateShipping(double subtotalAfterDiscount) {
        if (subtotalAfterDiscount < 0) {
            throw new IllegalArgumentException("Subtotal after discount must not be negative");
        }

        if (subtotalAfterDiscount >= config.shippingFreeThreshold()) {
            return 0.0;
        }

        return config.shippingStandardCost();
    }
}
