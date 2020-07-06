package Utility;

import Stuff.Commandable;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Map;

public class NewUser implements Runnable {
    private Socket clientSocket;
    private String newuser;
    public NewUser(Socket client){
        this.clientSocket=client;
    }
    @Override
    public void run() {
        try {
            SQLConnection sqlConnection = new SQLConnection();
            ServerSender serverSender =new ServerSender();
            ServerReceiver serverReceiver = new ServerReceiver();
            if (sqlConnection.ConnectionToDB()) {
                String key = "";
                boolean islogged = false;
                boolean regist = false;
                while (!(key.toUpperCase().equals("YES") || key.toUpperCase().equals("ДА") || key.toUpperCase().equals("NO") || key.toUpperCase().equals("НЕТ"))) {
                    serverSender.send(clientSocket, "Вы зарегистрированы как пользователь?(Да|Yes/Нет|No,регистр не важен)", 3);
                    key = (String) (serverReceiver.receive(clientSocket));
                }
                if (key.toUpperCase().equals("YES") || key.toUpperCase().equals("ДА")) {
                    key="";
                    while (!islogged) {
                        serverSender.send(clientSocket, "Введите логин", 3);
                        String login = (String) (serverReceiver.receive(clientSocket));
                        serverSender.send(clientSocket, "Введите пароль", 3);
                        String password = Collection.PasswordCoder((String) (serverReceiver.receive(clientSocket)));

                        if (sqlConnection.userExist(login, password)) {
                            newuser = login;
                            serverSender.send(clientSocket, "Вы успешно авторизованы!Теперь у вас есть доступ к информации о всех билетах и возможность изменять/добавлять свои билеты", 0);
                            islogged = true;
                            regist = false;
                        }
                        else {
                            serverSender.send(clientSocket, "Данные не были найдены.Перейти к регистрации нового пользователя?(Да|Yes/Нет|No,регистр не важен)", 3);
                            key = (String) (serverReceiver.receive(clientSocket));
                            if (key.toUpperCase().equals("YES") || key.toUpperCase().equals("ДА")) {
                                regist = true;
                                islogged = true;
                                key = "";
                            }
                            else {
                                Thread.sleep(20);
                                key = "";
                            }
                        }
                    }
                }
                islogged= false;
                if (key.toUpperCase().equals("NO") || key.toUpperCase().equals("НЕТ") || regist) {
                    boolean loginIsBusy = false;
                    while (!islogged) {
                        if (loginIsBusy){serverSender.send(clientSocket, "Имя пользователя занято,попробуйте ещё раз.\nВведите логин", 3);}
                        else serverSender.send(clientSocket, "Для работы потребуется регистрация.\nВведите логин", 3);
                        String login = (String) (serverReceiver.receive(clientSocket));
                        serverSender.send(clientSocket, "Введите пароль", 3);
                        String password = Collection.PasswordCoder((String) (serverReceiver.receive(clientSocket)));
                        if (sqlConnection.addNewUser(login, password)) {
                            serverSender.send(clientSocket, "Вы успешно зарегистрированы,теперь у вас есть доступ к информации о всех билетах и возможность изменять свои/добавлять свои билеты", 0);
                            islogged = true;
                            newuser = login;
                            Thread.sleep(20);
                        } else loginIsBusy = true;
                    }
                }

                try {
                    while (true) {
                        System.out.println("Ожидаю команду от человечка с адресом: " + clientSocket.getLocalAddress() + clientSocket.getPort());
                        Map<Commandable, String> commandStringMap;
                        Object o = serverReceiver.receive(clientSocket);
                        commandStringMap = (Map<Commandable, String>) o;
                        System.out.println("Выполняю команду " + commandStringMap.entrySet().iterator().next().getKey().getClass().getName()+" от человечка с адресом: " + clientSocket.getLocalAddress() + clientSocket.getPort());
                        serverSender.send(clientSocket,commandStringMap.entrySet().iterator().next().getKey().execute(commandStringMap.entrySet().iterator().next().getValue(),clientSocket,newuser),0);
                    }
                } catch (IOException|NullPointerException e){
                    e.printStackTrace();
                    System.out.println("Человечичек с адресом:"+clientSocket.getLocalAddress() + clientSocket.getPort()+" отключился");
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            ServerSender serverSender =new ServerSender();
            serverSender.send(clientSocket,"",4);
            System.out.println("Нет подключения к бд,принудительно отключаю клиента:"+clientSocket.getLocalAddress()+clientSocket.getPort());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e){
            e.printStackTrace();
            System.out.println("Человечичек с адресом:"+clientSocket.getLocalAddress() + clientSocket.getPort()+" отключился");
        }
    }
}