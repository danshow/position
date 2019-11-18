package com.demo.position.util;

import com.demo.position.exception.ValidationException;
import junit.framework.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PositionUtilTest {

    @Test
    public void givenStringNotUpperShouldReturnFalse() {
        String value = "aBc";
        Assert.assertFalse(PositionUtil.isStringUpper(value));
    }

    @Test
    public void givenUpperStringShouldReturnTrue() {
        String value = "CNY";
        Assert.assertTrue(PositionUtil.isStringUpper(value));
    }

    @Test(expected = ValidationException.class)
    public void givenIncorrectCnyThrowException() {
        String cny = "cny";
        PositionUtil.validateCurrency(cny);
    }

    @Test
    public void givenExistCnyShouldCalcTotal(){
        Map<String,Integer> positions=new HashMap<>();
        positions.put("HKD",100);
        positions.put("GPB",100);
        PositionUtil.calcPositions("HKD",22,positions);
        Assert.assertEquals(Integer.valueOf(122),positions.get("HKD"));
    }
    @Test
    public void givenNewCnyShouldInPosition(){
        Map<String,Integer> positions=new HashMap<>();
        positions.put("HKD",100);
        positions.put("GPB",100);
        PositionUtil.calcPositions("EUR",22,positions);
        Assert.assertEquals(Integer.valueOf(22),positions.get("EUR"));
    }
}
