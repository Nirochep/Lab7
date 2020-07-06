package Commands;

import Stuff.CommandWithoutArg;
import Stuff.Worker;
import Stuff.WorkerCollection;
import Utility.CreateWorker;
import Utility.SQLConnection;
import Utility.ServerReceiver;
import Utility.ServerSender;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.InputMismatchException;

/**
 * The type Remove greater.
 */
public class RemoveGreater implements CommandWithoutArg {
    /**
     * The Name.
     */
    String name = "remove_greater";
    /**
     * The Collection.
     */
    WorkerCollection collection = new WorkerCollection();
    /**
     * The Create worker.
     */


    /**
     * Check boolean.
     *
     * @param arg the arg
     * @return the boolean
     */
    public boolean check(Object arg) {
        return false;
    }

    /**
     * Gets new worker.
     *
     * @return the new worker
     */
    public Worker getNewWorker(Socket socket) throws IOException {CreateWorker CreateWorker = new CreateWorker();
    ServerReceiver serverReceiver = new ServerReceiver();
        if (!CreateWorker.isFromScript) return (Worker)serverReceiver.receive(socket);
        else return CreateWorker.workerFromScript;
    }

    @Override
    public String execute(Object o, Socket clientSocket, String user) throws SQLException {
        SQLConnection sqlConnection = new SQLConnection();
        sqlConnection.ConnectionToDB();
        sqlConnection.loadAllWorkers();
        try {
            ServerSender serverSender = new ServerSender();
            serverSender.send(clientSocket,"kek",1);
            Worker newWorker = this.getNewWorker(clientSocket);
            boolean tumb = false;
            String message = "";
            if (collection.getSize() == 0) return "Коллекция итак пустая.";
            else {
                Hashtable<Long, Worker> workers = collection.getCollection();
                Enumeration keys = workers.keys();
                while (keys.hasMoreElements()) {
                    Long k = (Long) keys.nextElement();
                        Worker v = workers.get(k);
                    if (v.compareTo(newWorker) > 0&&v.getUser().equals(user)) {
                        tumb = true;
                        collection.remove(k);
                        message += ("Работник с id:[" + k + "] успешно удален.");
                    }
                }
                sqlConnection.uploadAllWorkers();

                if (!tumb) return ("Работников,превышающих заданный не найдено.");
                else return message;
            }
        } catch (NumberFormatException | InputMismatchException e) {
            return ("Аргумент команды должен быть типа \"long\"");
        } catch (NullPointerException e) {
            return ("Неверно указаны данные.");
        } catch (IOException e) {
           return (e.getMessage());
        }
    }

    @Override
    public String getName() {
        return name;
    }
}

