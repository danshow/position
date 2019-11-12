package com.demo.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {

    private static List<Map<String, Integer>> data = new ArrayList<Map<String, Integer>>();
    static ThreadLocal<List> threadLocal = new InheritableThreadLocal<>();
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        threadLocal.set(data);
        parseArgs(args);
        logger.info("data : " + data);
        Thread displayThread = new Thread(() -> {
            while (true) {
                logger.info("display : {}", threadLocal.get());
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                }
            }
        });


        Thread inputThread = new Thread(() -> {
            while (true) {
                String line = parseInputLine();
                if ("done".equalsIgnoreCase(line)) {
                    logger.info("data input completed !");
                    return;
                }
                parseLine(line);
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                }
            }

        });
        inputThread.start();
//        displayThread.setDaemon(true);
        displayThread.start();

    }

    protected static void parseArgs(String[] args) {
//        while (true) {
        if (args.length == 1) {
            try {
                logger.info("start read file: " + args[0]);
                readFile(args[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (args.length > 1) {

//                String line = parseInputLine();
//                System.out.println("start parse line: " + line);
//                parseLine(line);
            logger.error("Error input ! param: file path  ");
            System.exit(-1);
            //        }
        }
    }


    protected static String parseInputLine() {
        String inputLine = null;
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("read line: ");
            inputLine = is.readLine();
            if (inputLine.length() == 0)
                System.out.println("Error empty input !");
            if (inputLine.equalsIgnoreCase("quit")) {
                System.exit(0);
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }

        return inputLine;

    }

    protected static void readFile(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String tmpStr = null;
            while ((tmpStr = br.readLine()) != null) {
                parseLine(tmpStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    protected static void parseLine(String line) {
        logger.info("parse line : " + line);
        String tmp = line.trim();
        String[] items = line.split(" ");
        if (items.length != 2) {
            System.err.println("line data parsed failed : " + tmp);
        }
        int amount = validateAmt(items[1]);
        if (validateCurrency(items[0]) && amount != 0) {
            Map<String, Integer> value = new HashMap<>();
            value.put(items[0], amount);
            data.add(value);
        }

    }

    protected static int validateAmt(String amt) {
        int amount = 0;
        try {
            amount = Integer.valueOf(amt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;

    }

    protected static boolean validateCurrency(String currency) {

        if (currency.length() != 3 && !isUpper(currency)) {
            System.err.println("invalid currency format: " + currency);
            return false;
        }
        return true;
    }

    protected static boolean isUpper(String currency) {
        char[] letters = currency.toCharArray();
        for (char letter : letters) {
            if (!Character.isUpperCase(letter)) {
                return false;
            }
        }
        return true;
    }
}
