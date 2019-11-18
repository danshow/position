package com.demo.position;

import com.demo.position.util.FileUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DisplayThread implements Runnable {
    private Map<String, Integer> positions;
    private Map<String, BigDecimal> rateWithUSD = new HashMap<>();
    private static final String RATE_FILE_NAME = "currencyRate";
    private static final int SLEEP_SECS = 60;


    public DisplayThread(Map<String, Integer> position) {
        this.positions = position;
    }


    @Override
    public void run() {
        while (true) {
            try {
                App.lock.lock();
                List<String> lines = FileUtils.readFile(RATE_FILE_NAME);
                FileUtils.parseRatesWithUSD(lines, rateWithUSD);
//                FileUtils.readCurrencyRate("currencyRate", rateWithUSD);
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
        
        for (Map.Entry<String, Integer> position : positions.entrySet()) {
            String currency = position.getKey();
            if (rates.containsKey(currency)) {
                BigDecimal valueWithUSD = rates.get(currency).multiply(new BigDecimal(position.getValue())).setScale(2);
                System.out.println(String.format("%s %s (USD %s)", currency, position.getValue(), valueWithUSD));
            } else {
                System.out.println(position.getKey() + "  " + position.getValue());
            }
        }
        System.out.println();
    }
}
