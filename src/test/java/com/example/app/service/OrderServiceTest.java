package com.example.app.service;

import com.example.app.config.AppConfig;
import com.example.app.model.CustomerType;
import com.example.app.model.OrderSummary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderServiceTest {

    private final OrderService orderService = new OrderService(
            new PriceCalculator(AppConfig.CONFIG),
            new ShippingCalculator(AppConfig.CONFIG)
    );

    @Test
    @DisplayName("Checkout returns full order summary for regular customer")
    void shouldBuildOrderSummaryForRegularCustomer() {
        OrderSummary summary = orderService.checkout(80.0, CustomerType.REGULAR);

        assertAll(
                () -> assertEquals(80.0, summary.subtotal()),
                () -> assertEquals(8.0, summary.discountAmount()),
                () -> assertEquals(72.0, summary.taxableAmount()),
                () -> assertEquals(15.12, summary.taxAmount()),
                () -> assertEquals(5.99, summary.shippingCost()),
                () -> assertEquals(93.11, summary.totalAmount())
        );
    }

    @Test
    @DisplayName("Checkout returns free shipping for large VIP order")
    void shouldBuildOrderSummaryForVipCustomerWithFreeShipping() {
        OrderSummary summary = orderService.checkout(150.0, CustomerType.VIP);

        assertAll(
                () -> assertEquals(30.0, summary.discountAmount()),
                () -> assertEquals(120.0, summary.taxableAmount()),
                () -> assertEquals(25.2, summary.taxAmount()),
                () -> assertEquals(0.0, summary.shippingCost()),
                () -> assertEquals(145.2, summary.totalAmount())
        );
    }
}
