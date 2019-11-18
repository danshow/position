package com.demo.position.util;

import com.demo.position.entity.Position;
import com.demo.position.exception.ValidationException;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

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

    protected static void calcPositions(String currency, int amount, List<Position> positions) {

        Iterator<Position> iterator = positions.iterator();
        boolean flag = false;
        int currAmt = 0;
        while (iterator.hasNext()) {
            Position position = iterator.next();
            if (currency.equalsIgnoreCase(position.getCurrencyCode())) {
                iterator.remove();
                flag = true;
                currAmt = position.getAmount();
                System.out.println("calc " + currency);
            }
        }
        if (flag == false) {
            positions.add(new Position(currency, amount));
        } else {
            positions.add(new Position(currency, currAmt + amount));
        }

    }
}
