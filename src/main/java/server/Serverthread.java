package server;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Serverthread implements Runnable{
    String message;
    String name;


    public Serverthread(ConcurrentHashMap<String, ClientHandler> userList) {
             this.userList = userList;
    }

    private ConcurrentHashMap<String,ClientHandler> userList;



    @Override
    public void run( ) {
        sendToAllUser(message, name);
        sendOnlineMessage();
    }


    public void sendToAllUser(String message,String name) {

        userList.values().forEach(clientHandler ->{clientHandler.messageToAll(message,name);});

    }

    public void sendOnlineMessage() {

        userList.values().forEach(clientHandler -> {clientHandler.sendOnlineMesage();});

    }

}
