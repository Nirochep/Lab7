package Stuff;


import Utility.ServerReceiver;
import Utility.ServerSender;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.net.Socket;
import java.sql.SQLException;

/**
 * The interface Commandable.
 */
public interface Commandable extends Serializable {
    /**
     * Execute.
     *
     * @param o the o
     * @throws FileNotFoundException the file not found exception
     */
    public String execute(Object o, Socket clientSocket,String user) throws  SQLException, FileNotFoundException;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName();
}