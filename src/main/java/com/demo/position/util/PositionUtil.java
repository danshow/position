package com.demo.position.util;

import com.demo.position.exception.ValidationException;

import java.math.BigDecimal;
import java.util.Map;

public class PositionUtil {

    public static int validateAmt(String amt) {
        int amount = Integer.valueOf(amt);
        return amount;
    }

    public static BigDecimal validateCnyRate(String amt) {
        BigDecimal amount = new BigDecimal(amt);
        return amount;
    }

    public static boolean validateCurrency(String currency) {

        if (currency.length() != 3 || (!isStringUpper(currency))) {
            throw new ValidationException("validate currency exception!");
        }
        return true;
    }

    public static boolean isStringUpper(String currency) {
        char[] letters = currency.toCharArray();
        for (char letter : letters) {
            if (!Character.isUpperCase(letter)) {
                return false;
            }
        }
        return true;
    }

    protected static void calcPositions(String currency, int amount, Map<String, Integer> positions) {
        if (positions.containsKey(currency)) {
            int value = positions.get(currency) + amount;
            positions.put(currency, value);
        } else {
            positions.put(currency, amount);
        }

    }
}
