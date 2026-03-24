package com.example.app.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Mutable;

@Config.Sources({"classpath:application.properties"})
public interface AppConfig extends Config, Mutable {

    AppConfig CONFIG = ConfigFactory.create(AppConfig.class);

    @Key("pricing.base-discount-percent")
    int baseDiscountPercent();

    @Key("pricing.vip-discount-percent")
    int vipDiscountPercent();

    @Key("pricing.tax-percent")
    int taxPercent();

    @Key("shipping.standard-cost")
    double shippingStandardCost();

    @Key("shipping.free-threshold")
    double shippingFreeThreshold();
}
