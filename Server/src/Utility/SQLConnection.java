package Utility;

import Stuff.*;
import javafx.scene.chart.ScatterChart;

import java.sql.*;
import java.util.Hashtable;
import java.util.TreeMap;

public class SQLConnection {
        private static final String url = "jdbc:postgresql://pg:5432/studs";
    private static final String user = "s251634";
    private static final String password = "crp189";
//    private static final String url = "jdbc:postgresql://localhost:5432/studs";
//    private static final String user = "postgres";
//    private static final String password = "123456";
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement preparedStatement;
    private static ResultSet rs;

    public Boolean ConnectionToDB() throws SQLException {
        try {
            connection = DriverManager.getConnection(url, user, password);
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    public Boolean userExist(String user, String password) {

        try {
            preparedStatement = connection.prepareStatement("select *  from users d where exists( select * from users where d.login = ? and d.password= ?)");
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            } else return false;
        } catch (SQLException e) {

        }
        return false;
    }

    public Boolean workerExist(Long id) {
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select *  from workers d where exists( select * from users where d.id =" + id + ")");
            if (rs.next()) {
                return true;
            } else return false;
        } catch (SQLException e) {

        }
        return false;
    }

    public Boolean addNewUser(String user, String password) {
        try {
            preparedStatement = connection.prepareStatement("insert into users values (?,?)");
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {

            return false;
        }
    }

    public void uploadAllWorkers() {
        WorkerCollection workerCollection = new WorkerCollection();
        try {
            stmt = connection.createStatement();
            stmt.execute("TRUNCATE workers");
            Hashtable<Long, Worker> ticketTreeMap = workerCollection.getCollection();
            ticketTreeMap.entrySet().stream().forEach(x -> {
                try {
                    preparedStatement = connection.prepareStatement("INSERT into workers values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    preparedStatement.setLong(1, x.getValue().getId());
                    preparedStatement.setString(2, x.getValue().getName());
                    preparedStatement.setFloat(3, x.getValue().getCoordinates().getX());
                    preparedStatement.setDouble(4, x.getValue().getCoordinates().getY());
                    preparedStatement.setTimestamp(5, (Timestamp) x.getValue().getCreationDate());
                    preparedStatement.setDouble(6, x.getValue().getSalary());
                    preparedStatement.setTimestamp(7, x.getValue().getStartDate());
                    preparedStatement.setTimestamp(8, x.getValue().getEndDate());
                    try {
                        preparedStatement.setString(9, x.getValue().getPosition().toString());
                    }catch (NullPointerException e){
                        preparedStatement.setString(9, null);
                    }
                    preparedStatement.setTimestamp(10, (Timestamp) x.getValue().getPerson().getBirthday());
                   try {
                       preparedStatement.setString(11, x.getValue().getPerson().getEyeColor().toString());
                   } catch (NullPointerException e){
                       preparedStatement.setString(11, null);
                   }
                    preparedStatement.setString(12, x.getValue().getPerson().getHairColor().toString());
                    preparedStatement.setString(13, x.getValue().getPerson().getNationality().toString());
                    preparedStatement.setString(14, x.getValue().getUser());
                    preparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadAllWorkers() {
        try {
            WorkerCollection.getLock().writeLock().lock();
            WorkerCollection workerCollection = new WorkerCollection();
            try{workerCollection.getCollection().clear();} catch (NullPointerException e){
                System.out.println("kek");}
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select * from workers");
            while (rs.next()) {
                Worker worker = new Worker();
                worker.setId(rs.getInt(1));
                worker.setName(rs.getString(2));
                Coordinates coordinates = new Coordinates();
                coordinates.setX(rs.getFloat(3));
                coordinates.setY(rs.getDouble(4));
                worker.setCoordinates(coordinates);
                worker.setCreationDate(rs.getTimestamp(5));
                worker.setSalary(rs.getDouble(6));
                worker.setStartDate(rs.getTimestamp(7));
                worker.setEndDate(rs.getTimestamp(8));
                try {
                    worker.setPosition(Position.valueOf(rs.getString(9)));
                }catch (NullPointerException e){
                    worker.setPosition(null);
                }
                Person person = new Person();
                person.setBirthday(rs.getTimestamp(10));
                try {
                    person.setEyeColor(Color.valueOf(rs.getString(11)));
                }catch (NullPointerException e){
                    person.setEyeColor(null);
                }
                person.setHairColor(Color.valueOf(rs.getString(12)));
                person.setNationality(Country.valueOf(rs.getString(13)));
                worker.setPerson(person);
                worker.setUser(rs.getString(14));
                workerCollection.insert(worker.getId(), worker);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            WorkerCollection.getLock().writeLock().unlock();
        }

    }
}