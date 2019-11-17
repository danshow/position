package com.demo.poistion.util;

import com.demo.position.exception.FileParseException;
import com.demo.position.exception.ValidationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class FileUtils {
    public static void readFile(String fileName, Map<String, Integer> positions)  {
        File file = new File(fileName);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String tmpStr = null;
            while ((tmpStr = br.readLine()) != null) {
                parseLine(tmpStr, positions);
            }
        } catch (IOException e) {
            e.printStackTrace();
//            throw e;
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (ValidationException ve) {
            ve.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void readCurrencyRate(String fileName, Map<String, BigDecimal> positions)  {
        File file = new File(fileName);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String tmpStr = null;
            while ((tmpStr = br.readLine()) != null) {
                String tmp = tmpStr.trim();
                String[] items = tmpStr.split(" ");
                if (items.length != 2) {
                    throw new FileParseException("line positions parsed failed for : " + tmp);
                }
                BigDecimal rate = PositionUtil.validateCnyRate(items[1]);
                if (PositionUtil.validateCurrency(items[0]) && !BigDecimal.ZERO.equals(rate)) {
                    positions.put(items[0],rate);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
//            throw e;
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (ValidationException ve) {
            ve.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static Map<String, Integer> parseLine(String line, Map<String, Integer> positions) {
//        logger.debug("parse line : " + line);
        String tmp = line.trim();
        String[] items = line.split(" ");
        if (items.length != 2) {
            throw new FileParseException("line positions parsed failed for : " + tmp);
        }
        int amount = PositionUtil.validateAmt(items[1]);
        if (PositionUtil.validateCurrency(items[0]) && amount != 0) {
            if (positions.containsKey(items[0])) {
                int value = positions.get(items[0]) + amount;
                positions.put(items[0], value);
            } else {
                positions.put(items[0], amount);
            }
        }
        return positions;
    }
}
