package com.example.restapi.service;

import com.example.restapi.util.MoneyUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PricingService {
    private final double taxRatePercent;
    private final double defaultDiscountPercent;

    public PricingService(
            @Value("${app.pricing.tax-rate-percent:8.0}") double taxRatePercent,
            @Value("${app.pricing.default-discount-percent:0.0}") double defaultDiscountPercent) {
        this.taxRatePercent = taxRatePercent;
        this.defaultDiscountPercent = defaultDiscountPercent;
    }

    public double finalizeTotal(double subtotal) {
        double discounted = MoneyUtils.applyDiscount(subtotal, defaultDiscountPercent);
        return MoneyUtils.round(discounted + MoneyUtils.tax(discounted, taxRatePercent));
    }
}
