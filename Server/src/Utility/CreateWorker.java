package Utility;


import Stuff.*;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * The type Create worker.
 */
public class CreateWorker {
    /**
     * The constant isFromScript.
     */
    public static boolean isFromScript;
    /**
     * The Scanner.
     */
    Scanner scanner = new Scanner(System.in);
    /**
     * The Why failed.
     */
    String whyFailed = "";
    /**
     * The constant workerFromScript.
     */
    public static Worker workerFromScript;
    public static String mistakes="";


    /**
     * Create from execute.
     *
     * @param o the o
     */
    public void createFromExecute(String o){
        Map.Entry entry = Decoder.decodeIntoCollectionFromExecute(o).entrySet().iterator().next();
        Worker worker = (Worker) entry.getValue();
        worker.setCreationDate(Timestamp.from(Instant.now()));
                workerFromScript = worker;

    }

    }