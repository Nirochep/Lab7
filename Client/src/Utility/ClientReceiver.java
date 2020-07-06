package Utility;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Map;

/**
 * The type Client receiver.
 */
public class ClientReceiver {
    public  static Socket socket;
    private static BufferedReader in;
    /**
     * The constant client.
     */

    private static ByteBuffer buffer = ByteBuffer.allocate(10000);
    public  static Object receive() throws IOException, ClassNotFoundException, SocketTimeoutException,  InterruptedException,SocketException{
    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Object obj = objectInputStream.readObject();
        CreateWorker createWorker = new CreateWorker();
        Map<Object, Integer> answerMap = (Map<Object, Integer>) obj;
        obj = answerMap.entrySet().iterator().next().getKey();
        int a = answerMap.entrySet().iterator().next().getValue();

        if (a == 0) {
            return obj;
        }
        else if (a == 1){
            System.out.println("Необходимо заполнить доп.данные для выполнения команды");
            ClientSender.send(createWorker.create());
            obj =ClientReceiver.receive();
        }
        else if (a == 2){
            System.out.println("Необходимо заполнить доп.данные для выполнения команды");
            ClientSender.send(createWorker.createPerson());
            obj = ClientReceiver.receive();
        }
        else if (a==3){
            System.out.println("Необходим ответ от вас: "+obj);
            String answer = Console.read();
            ClientSender.send(answer);
            obj = ClientReceiver.receive();
        }
        else if (a==4){
            System.out.println("На сервере нет подключения к базе данных,работа невозможна.");
            socket.close();
            System.exit(0);
        }
        return obj;
    }
}
