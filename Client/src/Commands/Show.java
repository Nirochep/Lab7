package Commands;

import Stuff.CommandWithoutArg;
import Stuff.Worker;
import Stuff.WorkerCollection;


import java.net.Socket;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

/**
 * The type Show.
 */
public class Show implements CommandWithoutArg {
    /**
     * The Name.
     */
    String name = "show";
    /**
     * The Collection.
     */
    WorkerCollection collection = new WorkerCollection();

    @Override
    public String execute(Object o, Socket clientSocket, String user) throws SQLException {
//        SQLConnection sqlConnection = new SQLConnection();
//        sqlConnection.ConnectionToDB();
//        sqlConnection.loadAllWorkers();
//        String m ="";
//        WorkerCollection.getLock().readLock().lock();
//        if (collection.getSize() == 0) return ("Коллекция пустая.");
//        else for (Map.Entry<Long, Worker> entry : collection.getCollection().entrySet())
//            m+=(entry.getValue().getInfo()+"\n");
//        WorkerCollection.getLock().readLock().unlock();
        return null;
    }


    @Override
    public String getName() {
        return name;
    }
}