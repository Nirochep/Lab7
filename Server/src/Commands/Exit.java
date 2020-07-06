package Commands;

import Stuff.CommandWithoutArg;
import Stuff.WorkerCollection;

import java.io.FileNotFoundException;
import java.net.Socket;

/**
 * The type Exit.
 */
public class Exit implements CommandWithoutArg {
    /**
     * The Name.
     */
    String name = "exit";
    /**
     * The Worker collection.
     */
    WorkerCollection WorkerCollection = new WorkerCollection();

    @Override
    public String execute(Object o, Socket clientSocket, String user) {
        System.out.println("Клиент с адресом "+clientSocket.getLocalAddress()+clientSocket.getPort()+" попросил завершить работу - разрешаю.");
        return null;
    }

    @Override
    public String getName() {
        return name;
    }
}