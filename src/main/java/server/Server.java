package server;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Server implements Runnable {

    private ServerSocket serverSocket;
    private Socket socket;
    static ConcurrentHashMap<String,ClientHandler> userList = new ConcurrentHashMap<>(10);

    @Override
    public void run() {


    }





    public void sendToSpecificUsers(String message, String users) {
        String[] usersToSendTo = users.split(",");
        for(String user: usersToSendTo){
            ClientHandler ch =  userList.get(user);
            ch.messageToAll(message,users);
        }
    }

    public void sendToAllUser(String message,String name) {
      ClientHandler ch = userList.get(name);
      ch.sendOnlineMesage();



        //userList.keySet().forEach(clientHandler ->{clientHandler.messageToAll(message,name);});

    }

    public void sendOnlineMessage() {
        ClientHandler ch = userList.get(userList);
        ch.sendOnlineMesage();

       // userList.keySet().forEach(name -> {clienthandler.sendOnlineMesage();});


    }


    public void startSever(int port) throws IOException {


        serverSocket = new ServerSocket(port);
        System.out.println("Server started, listening on : " + port);
        while (true) {
            System.out.println("Waiting for a client");
            socket = serverSocket.accept();      //Blocking call
            System.out.println("New client connected");
            ClientHandler clientHandler = new ClientHandler(socket,userList,this);

            Thread thread = new Thread(clientHandler);
            thread.start();

        }

    }


}
