package com.example.app.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({"classpath:test.properties"})
public interface TestConfig extends Config {

    TestConfig CONFIG = ConfigFactory.create(TestConfig.class);

    @Key("expected.base-discount-percent")
    int baseDiscountPercent();

    @Key("expected.vip-discount-percent")
    int vipDiscountPercent();

    @Key("expected.tax-percent")
    int taxPercent();

    @Key("expected.shipping.standard-cost")
    double shippingStandardCost();

    @Key("expected.shipping.free-threshold")
    double shippingFreeThreshold();
}
