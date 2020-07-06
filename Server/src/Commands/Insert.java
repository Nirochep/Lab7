package Commands;

import Stuff.CommandWithoutArg;
import Stuff.Worker;
import Stuff.WorkerCollection;
import Utility.SQLConnection;
import Utility.ServerReceiver;
import Utility.ServerSender;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.InputMismatchException;

/**
 * The type Insert.
 */
public class Insert implements CommandWithoutArg {
    /**
     * The Name.
     */
    String name = "insert";
    /**
     * The Worker collection.
     */
    WorkerCollection WorkerCollection = new WorkerCollection();
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
        return WorkerCollection.isKeyFree((Long) arg);
    }


    /**
     * Gets new worker.
     *
     * @return the new worker
     */
    public Worker getNewWorker(Socket socket) throws IOException {
        ServerReceiver serverReceiver = new ServerReceiver();
        if (!Utility.CreateWorker.isFromScript) return (Worker) serverReceiver.receive(socket);
        else return Utility.CreateWorker.workerFromScript;
    }

    @Override
    public String execute(Object o, Socket clientSocket, String user) throws SQLException {
        try {

            SQLConnection sqlConnection = new SQLConnection();

            sqlConnection.ConnectionToDB();

            sqlConnection.loadAllWorkers();

            ServerSender serverSender = new ServerSender();

            if (!Utility.CreateWorker.isFromScript) serverSender.send(clientSocket, "kek", 1);

            Worker Worker = this.getNewWorker(clientSocket);

            Worker.setId(WorkerCollection.generateId());

            Worker.setUser(user);

            WorkerCollection.insert(Worker.getId(), Worker);

            sqlConnection.uploadAllWorkers();

            return ("Работник добавлен в коллекцию!");
        } catch (NumberFormatException | InputMismatchException | IOException e) {
            return ("Аргумент команды должен быть типа \"long\"");
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
