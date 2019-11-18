package com.demo.position;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DisplayThreadTest {

    @Test
    public void testPositionDisplay() {
        new DisplayThread(buildPositios()).display(buildRateWithUSD());
    }

    private Map<String, Integer> buildPositios() {
        Map<String, Integer> positions = new HashMap<>();
        positions.put("CNY", 1000);
        positions.put("HKD", 2000);
        positions.put("GBP", 300);
        positions.put("EUR",111);
        positions.put("USD",22);
        return positions;
    }

    private Map<String, BigDecimal> buildRateWithUSD() {
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("HKD", BigDecimal.valueOf(0.13));
        rates.put("CNY", BigDecimal.valueOf(0.145));
        rates.put("GBP", BigDecimal.valueOf(1.13));
        return rates;
    }
}
