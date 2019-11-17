package com.demo.position;

import com.demo.poistion.util.FileUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DisplayThread implements Runnable {
    private Map<String, Integer> positions;
    private Map<String, BigDecimal> rateWithUSD = new HashMap<>();


    public DisplayThread(Map<String, Integer> position) {
        this.positions = position;
    }


    @Override
    public void run() {
        while (true) {
            try {
                App.lock.lock();
                FileUtils.readCurrencyRate("currencyRate", rateWithUSD);
                display(positions);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                App.lock.unlock();
            }
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
            }

        }
    }

    protected void display(Map<String, Integer> positionList) {
        for (Map.Entry<String, Integer> position : positionList.entrySet()) {
            String currency = position.getKey();
            if (rateWithUSD.containsKey(currency)) {
                BigDecimal valueWithUSD = rateWithUSD.get(currency).multiply(new BigDecimal(position.getValue())).setScale(2);
                System.out.println(String.format("%s %s (USD %s)", currency, position.getValue(), valueWithUSD));
            } else {
                System.out.println(position.getKey() + "  " + position.getValue());
            }
        }
        System.out.println("");
    }
}
