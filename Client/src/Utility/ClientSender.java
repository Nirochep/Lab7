package Utility;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;

public class ClientSender {
    public static Boolean serverisconnected = false;
    public static void send(Object o) throws SocketException, ClassNotFoundException, InterruptedException {
        try {

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(ClientReceiver.socket.getOutputStream());
            objectOutputStream.writeObject(o);

        } catch (IOException e) {
            serverisconnected = false;
            ClientSender.tryToConnect();
        }
    }
    public static void tryToConnect() throws InterruptedException, ClassNotFoundException {
        while(!serverisconnected)
            try {

                Socket socket = new Socket("localhost", 3513);
                socket.setSoTimeout(3000);
                serverisconnected = true;
                System.out.println("Подключился к серверу");
                ClientReceiver.socket = socket ;
                socket.setSoTimeout(0);

            }
            catch (ConnectException e){
                System.out.println("Сервер отключен или недоступен,попробуйте позже.");
                Thread.sleep(2000);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}