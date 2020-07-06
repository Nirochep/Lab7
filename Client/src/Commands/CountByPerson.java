package Commands;

import Stuff.CommandWithoutArg;
import Stuff.Worker;
import Stuff.WorkerCollection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

/**
 * The type Count by person.
 */
public class CountByPerson implements CommandWithoutArg {
    String name = "count_by_person";
    WorkerCollection collection = new WorkerCollection();


    public boolean check(Object arg) {
        return collection.isKeyFree((Long) arg);
    }


    /**
     * Gets new worker.
     *
     * @return the new worker
     */
    public Worker getNewWorker(Socket socket) throws IOException {
        //CreateWorker CreateWorker = new CreateWorker();
//        ServerReceiver serverReceiver = new ServerReceiver();
//        if (!CreateWorker.isFromScript) return (Worker) serverReceiver.receive(socket);
//        else return CreateWorker.workerFromScript;
        return null;
    }

    @Override
    public String execute(Object o, Socket clientSocket, String user) throws FileNotFoundException, SQLException {
//        try{
//            SQLConnection sqlConnection = new SQLConnection();
//            sqlConnection.ConnectionToDB();
//            sqlConnection.loadAllWorkers();
//            int i;
//            ServerSender serverSender = new ServerSender();
//            serverSender.send(clientSocket,"kek",2);
//            Worker Worker = this.getNewWorker(clientSocket);
//            i = (int) collection.getCollection().entrySet().stream().filter(x -> x.getValue().getPerson().equals(Worker.getPerson())).count();
//            return ("Указанных вами людей в коллекции: "+i);
//        }catch (NullPointerException | IOException e){
//            return ("Неверно указаны данные.");
//        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }
}
