package com.demo.position;

import com.demo.position.entity.Position;
import com.demo.position.util.FileUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DisplayThread implements Runnable {
    private List<Position> positions;
    private Map<String, BigDecimal> rateWithUSD = new HashMap<>();
    private static final String RATE_FILE_NAME = "currencyRate";
    private static final int SLEEP_SECS = 10;


    public DisplayThread(List<Position> positions) {
        this.positions = positions;
    }


    @Override
    public void run() {
        while (true) {
            try {
                App.lock.lock();
                List<String> lines = FileUtils.readFile(RATE_FILE_NAME);
                FileUtils.parseRatesWithUSD(lines, rateWithUSD);
                display(rateWithUSD);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                App.lock.unlock();
            }
            try {
                TimeUnit.SECONDS.sleep(SLEEP_SECS);
            } catch (InterruptedException e) {
            }

        }
    }

    protected void display(Map<String, BigDecimal> rates) {
        System.out.println();
        if (positions == null) {
            return;
        }
        for (Position position : positions) {
            String currency = position.getCurrencyCode();
            if (rates.containsKey(currency)) {
                BigDecimal valueWithUSD = rates.get(currency).multiply(new BigDecimal(position.getAmount())).setScale(2);
                System.out.println(String.format("%s %s (USD %s)", currency, position.getAmount(), valueWithUSD));
            } else {
                System.out.println(position);
            }
        }
        System.out.println();
    }
}
