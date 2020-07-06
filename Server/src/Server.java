import Stuff.Worker;
import Stuff.WorkerCollection;
import Utility.Collection;
import Utility.NewUser;
import Utility.SQLConnection;
import Utility.WriterToFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ForkJoinPool;

/**
 * The type Main.
 */
public class Server {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws FileNotFoundException the file not found exception
     * @author Max
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, SQLException {
        Collection.create();

        WorkerCollection workerCollection = new WorkerCollection();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        while (!Collection.server.isClosed()) {
            Socket socket = Collection.server.accept();
            forkJoinPool.execute(new NewUser(socket));
            System.out.println("Новый человечек: "+socket.getLocalAddress()+socket.getPort());
        }

    }
}


