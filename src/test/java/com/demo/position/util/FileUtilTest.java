package com.demo.position.util;

import com.demo.position.exception.ParseException;
import com.demo.position.exception.ValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FileUtilTest {

    Map<String, Integer> positions = new HashMap<>();

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
        Map<String, Integer> pos = FileUtils.parsePosition(line, positions);
        Assert.assertTrue(positions.containsKey("CNY"));
        Assert.assertEquals(Integer.valueOf(111), positions.get("CNY"));
    }


}
