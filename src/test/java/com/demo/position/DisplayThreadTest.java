package com.demo.position;

import com.demo.position.entity.Position;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayThreadTest {

    @Test
    public void testPositionDisplay() {
        new DisplayThread(buildPositios()).display(buildRateWithUSD());
    }

    private List<Position> buildPositios() {
        List<Position> positions = new ArrayList<>();
        positions.add(new Position("CNY", 1000));
        positions.add(new Position("HKD", 2000));
        positions.add(new Position("GBP", 300));
        positions.add(new Position("EUR", 111));
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
