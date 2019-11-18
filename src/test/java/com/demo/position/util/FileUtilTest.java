package com.demo.position.util;

import com.demo.position.entity.Position;
import com.demo.position.exception.ParseException;
import com.demo.position.exception.ValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FileUtilTest {

    List<Position> positions = new ArrayList<>();

    @Test(expected = ParseException.class)
    public void givenErrInputLineShouldReturnException() {

        String line = "test";
        FileUtils.parsePosition(line, positions);

    }

    @Test(expected = ValidationException.class)
    public void givenIncorrectCnyShouldParseFail() {
        String line = "cny 111";
        FileUtils.parsePosition(line, positions);
    }

    @Test(expected = NumberFormatException.class)
    public void givenIncorrectPositionShouldParseFail() {
        String line = "CNY A111";
        FileUtils.parsePosition(line, positions);
    }

    @Test
    public void parseCorrectPosition() {

        String line = "CNY 111";
        FileUtils.parsePosition(line, positions);
        Position position = positions.get(0);
        Assert.assertEquals("CNY", position.getCurrencyCode());
        Assert.assertEquals(111, position.getAmount());
    }


}
