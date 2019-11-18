package com.demo.position.util;

import com.demo.position.entity.Position;
import com.demo.position.exception.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileUtils {

    public static List<String> readFile(String fileName) {
        List<String> lines = new ArrayList<>();
        File file = new File(fileName);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String tmpStr;
            while ((tmpStr = br.readLine()) != null) {
                lines.add(tmpStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }


    public static void parseRatesWithUSD(List<String> lines, Map<String, BigDecimal> rates) {
        lines.stream().forEach(item -> {
            String tmp = item.trim();
            String[] items = item.split(" ");
            if (items.length != 2) {
                throw new ParseException("line positions parsed failed for : " + tmp);
            }
            BigDecimal rate = PositionUtil.validateCnyRate(items[1]);
            if (PositionUtil.validateCurrency(items[0]) && !BigDecimal.ZERO.equals(rate)) {
                rates.put(items[0], rate);
            }
        });
    }

    public static void parsePosition(String line, List<Position> positions) {
        String tmp = line.trim();
        String[] items = line.split(" ");
        if (items.length != 2) {
            throw new ParseException("line positions parsed failed for : " + tmp);
        }
        int amount = PositionUtil.validateAmt(items[1]);
        String currency = items[0];
        if (PositionUtil.validateCurrency(currency) && amount != 0) {
            PositionUtil.calcPositions(currency, amount, positions);
        }
    }


}
