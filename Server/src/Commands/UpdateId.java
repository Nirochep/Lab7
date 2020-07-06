package Commands;

import Stuff.*;
import Utility.CreateWorker;
import Utility.SQLConnection;
import Utility.ServerReceiver;
import Utility.ServerSender;
import jdk.net.Sockets;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

/**
 * The type Update id.
 */
public class UpdateId implements Commandable {
    /**
     * The Name.
     */
    String name = "update";
    /**
     * The Create worker.
     */

    /**
     * The Collection.
     */
    WorkerCollection collection = new WorkerCollection();

    /**
     * Check boolean.
     *
     * @param arg the arg
     * @return the boolean
     */
    public boolean check(Object arg) {
        return !collection.isKeyFree((Long) arg);
    }

    /**
     * Gets new worker.
     *
     * @return the new worker
     */
    public Worker getNewWorker(Socket socket) throws IOException { CreateWorker CreateWorker = new CreateWorker();
        ServerReceiver serverReceiver = new ServerReceiver();
        if (!CreateWorker.isFromScript) return (Worker) serverReceiver.receive(socket);
        else return CreateWorker.workerFromScript;
    }

    @Override
    public String execute(Object o, Socket clientSocket, String user) throws FileNotFoundException, SQLException {
        SQLConnection sqlConnection = new SQLConnection();
        sqlConnection.ConnectionToDB();
        sqlConnection.loadAllWorkers();
        try {
            long id = Long.parseLong((String) o);
                Worker worker = collection.getCollection().entrySet().stream().filter(x -> x.getKey() == id).findFirst().get().getValue();
            if (this.check(id)&&worker.getUser().equals(user)) {
                ServerSender serverSender = new ServerSender();
                serverSender.send(clientSocket,"kek",1);
                Worker newWorker = this.getNewWorker(clientSocket);
                if(sqlConnection.workerExist(id)){
                if (newWorker != null) {
                    newWorker.setId(id);
                    newWorker.setUser(user);
                    collection.update(id, newWorker);
                    sqlConnection.uploadAllWorkers();
                    return ("Работник c id[" + o + "] успешно обновлен.");
                } else return "Что-то пошло не так";
                } else return "Работник с id[" + o + "] был удалён,пока вы вводили новые данные для него,кто же это мог сделать??????";
            } else return ("Работник с id[" + o + "] не существует или не принадлежит вам.");
        } catch (NumberFormatException | InputMismatchException e) {
            e.printStackTrace();
            return ("Аргумент команды должен быть типа \"long\"");
        }
        catch (NullPointerException e){
            return ("Неверно указаны данные.");
        } catch (IOException e) {
            return (e.getMessage());
        }
        catch (NoSuchElementException e){
            return ("Работник с id[" + o + "] не существует");
        }

    }

    @Override
    public String getName() {
        return name;
    }
}
