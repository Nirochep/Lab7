package Commands;

import Stuff.CommandWithoutArg;
import Stuff.WorkerCollection;

import java.net.Socket;
import java.sql.SQLException;

/**
 * The type Info.
 */
public class Info implements CommandWithoutArg {
    /**
     * The Name.
     */
    String name = "info";
    /**
     * The Worker collection.
     */
    WorkerCollection WorkerCollection = new WorkerCollection();

    @Override
    public String execute(Object o, Socket clientSocket, String user) throws SQLException {
        return (WorkerCollection.getInfo());
    }

    @Override
    public String getName() {
        return name;
    }
}