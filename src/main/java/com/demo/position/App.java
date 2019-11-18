package com.demo.position;

import com.demo.position.util.FileUtils;
import com.demo.position.exception.ParseException;
import com.demo.position.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * position demo
 * Author by bill dan
 */
public class App {

    private static Map<String, Integer> positions = new HashMap<>();
    static ThreadLocal<Map> threadLocal = new InheritableThreadLocal<>();
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    static Lock lock = new ReentrantLock();


    public static void main(String[] args) {

        //to sync position in multi thread.
        threadLocal.set(positions);
        parseArgs(args);

        Thread display = new Thread(new DisplayThread(positions));


        Thread inputThread = new Thread(() -> {
            while (true) {
                try {
                    String line = getInput();
                    FileUtils.parsePosition(line, positions);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                } catch (ParseException fpe) {
                    fpe.printStackTrace();
                } catch (ValidationException ve) {
                    ve.printStackTrace();
                }
            }

        });


        display.start();
        inputThread.start();

    }


    protected static void parseArgs(String[] args) {

        if (args.length == 1) {
            logger.info("start read file: " + args[0]);
            List<String> lines = FileUtils.readFile(args[0]);
            lines.stream().forEach(item -> {
                FileUtils.parsePosition(item, positions);
            });
        }
        if (args.length > 1) {
            logger.error("Error input ! param: file path  ");
            System.exit(-1);
        }
    }

    protected static String getInput() {
        String inputLine = null;
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            inputLine = is.readLine();
            lock.lock();
            if (inputLine.length() == 0)
                throw new ValidationException("Error empty input !");
            if (inputLine.equalsIgnoreCase("quit")) {
                System.exit(0);
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e);
        } finally {
            lock.unlock();
        }

        return inputLine;

    }


}
