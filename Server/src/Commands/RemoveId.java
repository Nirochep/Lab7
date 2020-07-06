package Commands;

import Stuff.Commandable;
import Stuff.Worker;
import Stuff.WorkerCollection;
import Utility.SQLConnection;

import java.io.FileNotFoundException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The type Remove id.
 */
public class RemoveId implements Commandable {
    /**
     * The Name.
     */
    String name = "remove";
    /**
     * The Collection.
     */
    WorkerCollection collection = new WorkerCollection();

    @Override
    public String execute(Object o, Socket clientSocket, String user) throws FileNotFoundException, SQLException {
        SQLConnection sqlConnection = new SQLConnection();
        sqlConnection.ConnectionToDB();
        sqlConnection.loadAllWorkers();
        try {
            long id = Long.parseLong((String) o);
            if (collection.getSize() == 0) return ("Коллекция итак пустая.");
            else {

                Worker worker = collection.getCollection().entrySet().stream().filter(x->x.getKey()==id).findFirst().get().getValue();
                if (worker == null) {
                    return "Работника с таким id не найдено";
                } else if (!worker.getUser().equals(user)) {
                    return "Данный работник не принадлежит вам";
                } else {
                    collection.remove(id);
                    sqlConnection.uploadAllWorkers();

                    return ("Элемент с id[" + o + "] успешно удален.");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return ("Аргумент команды должен быть типа \"long\"");
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
