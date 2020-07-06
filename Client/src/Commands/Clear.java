package Commands;

import Stuff.CommandWithoutArg;
import Stuff.Worker;
import Stuff.WorkerCollection;

import java.io.FileNotFoundException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Hashtable;

/**
 * The type Clear.
 */
public class Clear implements CommandWithoutArg {
    /**
     * The Name.
     */
    String name = "clear";
    /**
     * The Collection.
     */
    WorkerCollection collection = new WorkerCollection();

    @Override
    public String execute(Object o, Socket clientSocket, String user) throws SQLException {
//        if (collection.getSize() == 0) return ("Коллекция итак пустая.");
//        else {
//            Hashtable<Long, Worker> coll= new Hashtable<>();
//            collection.getCollection().entrySet().stream().filter(x->x.getValue().getUser().equals(user)).forEach(x->coll.put(x.getKey(),x.getValue()));
//            if (coll.size()==0) return  "Ваших билетов нет в коллекции";
//            else {
//                coll.entrySet().stream().forEach(x->collection.remove(x.getKey()));
//                return "Ваши билеты удалены.";
//            }
//        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }
}
