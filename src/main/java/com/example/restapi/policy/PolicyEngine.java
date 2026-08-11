package com.example.restapi.policy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class PolicyEngine {
    private final List<DiscountPolicy> discounts = new CopyOnWriteArrayList<>();
    private final List<ShippingPolicy> shipping = new CopyOnWriteArrayList<>();

    public DiscountPolicy addDiscount(DiscountPolicy policy) {
        discounts.add(policy);
        return policy;
    }

    public ShippingPolicy addShipping(ShippingPolicy policy) {
        shipping.add(policy);
        return policy;
    }

    public Optional<DiscountPolicy> bestDiscount(BigDecimal orderAmount) {
        return discounts.stream()
                .filter(p -> p.appliesTo(orderAmount))
                .max(Comparator.comparing(p -> p.discountFor(orderAmount)));
    }

    public BigDecimal applyBestDiscount(BigDecimal orderAmount) {
        return bestDiscount(orderAmount).map(p -> p.netAmount(orderAmount)).orElse(orderAmount);
    }

    public Optional<ShippingPolicy> shippingFor(String region) {
        String needle = region == null ? "" : region.trim().toUpperCase();
        return shipping.stream().filter(p -> p.getRegion().equals(needle)).findFirst();
    }

    public BigDecimal shippingQuote(String region, BigDecimal orderAmount) {
        return shippingFor(region).map(p -> p.quote(orderAmount)).orElse(BigDecimal.valueOf(9.99));
    }

    public List<DiscountPolicy> discounts() { return List.copyOf(new ArrayList<>(discounts)); }

    public List<ShippingPolicy> shippingPolicies() { return List.copyOf(new ArrayList<>(shipping)); }

    public int policyCount() { return discounts.size() + shipping.size(); }
}
