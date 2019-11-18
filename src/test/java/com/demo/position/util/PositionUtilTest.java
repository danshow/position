package com.demo.position.util;

import com.demo.position.entity.Position;
import com.demo.position.exception.ValidationException;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
    public void givenExistCnyShouldCalcTotal() {
        List<Position> positions = new ArrayList<>();
        positions.add(new Position("HKD", 100));
        PositionUtil.calcPositions("HKD", 22, positions);

        Assert.assertEquals(122, positions.get(0).getAmount());
    }

    @Test
    public void givenNewCnyShouldInPosition() {
        List<Position> positions = new ArrayList<>();
        positions.add(new Position("HKD", 100));
        PositionUtil.calcPositions("EUR", 22, positions);
        Assert.assertEquals(22, positions.get(1).getAmount());
    }
}
