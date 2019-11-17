package com.demo.position;

import com.demo.poistion.util.FileUtils;
import com.demo.poistion.util.PositionUtil;
import com.demo.position.exception.FileParseException;
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
        display.start();


        Thread inputThread = new Thread(() -> {
            while (true) {
                try {
                    String line = getInput(lock);
                    FileUtils.parseLine(line, positions);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                } catch (FileParseException fpe) {
                    fpe.printStackTrace();
                } catch (ValidationException ve) {
                    ve.printStackTrace();
                }

            }

        });
        inputThread.start();

    }


    protected static void parseArgs(String[] args) {

        if (args.length == 1) {
            logger.info("start read file: " + args[0]);
            FileUtils.readFile(args[0], positions);
        }
        if (args.length > 1) {
            logger.error("Error input ! param: file path  ");
            System.exit(-1);
        }
    }

    protected static String getInput(Lock lock) {
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
