package Commands;

import Stuff.Commandable;
import Stuff.Worker;
import Stuff.WorkerCollection;

import java.io.FileNotFoundException;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

/**
 * The type Filter less than end date.
 */
public class FilterLessThanEndDate implements Commandable {
    /**
     * The Name.
     */
    String name = "filter_less_than_end_date";
    /**
     * The Collection.
     */
    WorkerCollection collection = new WorkerCollection();
    @Override
    public String execute(Object o, Socket clientSocket, String user) throws FileNotFoundException {
//
        return null;
    }

    @Override
    public String getName() {
        return name;
    }
}
