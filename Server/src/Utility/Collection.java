package Utility;

import Stuff.*;
import Commands.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * The type Collection.
 */
public class Collection {
    /**
     * The Collection.
     */
    public static ServerSocket server;
    Hashtable<Long,Worker> collection;

    /**
     * Instantiates a new Collection.
     */
    public Collection() {
        collection = new Hashtable<>();
        WorkerCollection.setCollection(collection);
        System.out.println("Коллекция создана. ");
    }
    public static void checkForExitCommand() throws IOException {
        Thread backgroundReaderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Запускаю проверку на выход по команде exit в консоли.");
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
                    while (!Thread.interrupted()) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        if (line.equalsIgnoreCase("exit")) {
                            System.out.println("Я отключаюсь,всем ня пока:(");
                            System.exit(0);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        backgroundReaderThread.setDaemon(true);
        backgroundReaderThread.start();
    }
    public static void create() throws IOException {
        Collection collection = new Collection();
        Commands commands = new Commands();
        Collection.checkForExitCommand();
        commands.regist(new Show(),new Exit(),new Info(),new Help(),new Clear(),new Insert(),new UpdateId(),new RemoveGreater(),new RemoveId(),new RemoveLower(),new MaxByEndDate(),new FilterLessThanEndDate(),new CountByPerson(),new ExecuteScript());
        try {
            server = new ServerSocket(3513);
            System.out.println("Сервер запущен");
        } catch (BindException e){

            System.out.println("Данный порт уже занят,возможно сервер уже запущен!\n Принудительно завершаю работу.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("ОШИБКА чегото там");
        }

    }
    public static String PasswordCoder(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NullPointerException ignored){

        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }
}