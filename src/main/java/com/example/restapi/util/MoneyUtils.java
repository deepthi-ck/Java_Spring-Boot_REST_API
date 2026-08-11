package com.example.restapi.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MoneyUtils {
    private MoneyUtils() {}

    public static double round(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static double applyDiscount(double amount, double percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("discount percent out of range");
        }
        return round(amount * (1.0 - (percent / 100.0)));
    }

    public static double tax(double amount, double ratePercent) {
        return round(amount * (ratePercent / 100.0));
    }
}
