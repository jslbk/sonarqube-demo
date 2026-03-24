package com.example.app.service;

import com.example.app.config.AppConfig;
import com.example.app.config.TestConfig;
import com.example.app.model.CustomerType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriceCalculatorTest {

    private final AppConfig appConfig = AppConfig.CONFIG;
    private final TestConfig testConfig = TestConfig.CONFIG;
    private final PriceCalculator calculator = new PriceCalculator(appConfig);

    @Test
    @DisplayName("Regular customer discount is taken from Owner configuration")
    void shouldCalculateRegularDiscountFromConfig() {
        double subtotal = 200.00;
        double expectedDiscount = subtotal * testConfig.baseDiscountPercent() / 100.0;

        assertEquals(expectedDiscount, calculator.calculateDiscount(subtotal, CustomerType.REGULAR));
    }

    @Test
    @DisplayName("VIP customer discount is taken from Owner configuration")
    void shouldCalculateVipDiscountFromConfig() {
        double subtotal = 200.00;
        double expectedDiscount = subtotal * testConfig.vipDiscountPercent() / 100.0;

        assertEquals(expectedDiscount, calculator.calculateDiscount(subtotal, CustomerType.VIP));
    }

    @Test
    @DisplayName("Tax is calculated from Owner configuration")
    void shouldCalculateTaxFromConfig() {
        double taxableAmount = 100.00;
        double expectedTax = taxableAmount * testConfig.taxPercent() / 100.0;

        assertEquals(expectedTax, calculator.calculateTax(taxableAmount));
    }

    @Test
    @DisplayName("Negative subtotal is rejected")
    void shouldRejectNegativeSubtotal() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateDiscount(-1, CustomerType.REGULAR)
        );

        assertEquals("Subtotal must not be negative", exception.getMessage());
    }
}
