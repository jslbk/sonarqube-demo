package com.example.app.service;

import com.example.app.config.AppConfig;
import com.example.app.config.TestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShippingCalculatorTest {

    private final TestConfig testConfig = TestConfig.CONFIG;
    private final ShippingCalculator shippingCalculator = new ShippingCalculator(AppConfig.CONFIG);

    @Test
    @DisplayName("Shipping is free when subtotal reaches threshold from config")
    void shouldReturnFreeShipping() {
        assertEquals(0.0, shippingCalculator.calculateShipping(testConfig.shippingFreeThreshold()));
    }

    @Test
    @DisplayName("Standard shipping cost is taken from config")
    void shouldReturnStandardShippingCost() {
        assertEquals(testConfig.shippingStandardCost(), shippingCalculator.calculateShipping(40.0));
    }

    @Test
    @DisplayName("Negative subtotal after discount is rejected")
    void shouldRejectNegativeSubtotalAfterDiscount() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> shippingCalculator.calculateShipping(-0.01)
        );

        assertEquals("Subtotal after discount must not be negative", exception.getMessage());
    }
}
