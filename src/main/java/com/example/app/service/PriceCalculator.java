package com.example.app.service;

import com.example.app.config.AppConfig;
import com.example.app.model.CustomerType;

public class PriceCalculator {

    private final AppConfig config;

    public PriceCalculator(AppConfig config) {
        this.config = config;
    }

    public double calculateDiscount(double subtotal, CustomerType customerType) {
        validateSubtotal(subtotal);
        int discountPercent = customerType == CustomerType.VIP
                ? config.vipDiscountPercent()
                : config.baseDiscountPercent();
        return round(subtotal * discountPercent / 100.0);
    }

    public double calculateTax(double taxableAmount) {
        validateSubtotal(taxableAmount);
        return round(taxableAmount * config.taxPercent() / 100.0);
    }

    private void validateSubtotal(double subtotal) {
        if (subtotal < 0) {
            throw new IllegalArgumentException("Subtotal must not be negative");
        }
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
