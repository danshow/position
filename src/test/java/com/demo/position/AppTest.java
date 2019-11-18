package com.demo.position;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest {


    @Test
    public void givenWrongPathsShouldPrintException() {
        String[] args = new String[1];
        args[0] = "wrong args";
        App.parseArgs(args);
    }
}
