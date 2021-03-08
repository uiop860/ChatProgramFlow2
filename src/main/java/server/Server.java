package server;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    static ConcurrentHashMap<ClientHandler, String> userList = new ConcurrentHashMap<>(10);


    public void sendToSpecificUsers(String message, String[] users) {


    }

    public void sendToAllUser(String message,String name) {

        userList.keySet().forEach(clientHandler ->{clientHandler.messageToAll(message,name);});

    }

    public void sendOnlineMessage() {

        userList.keySet().forEach(clientHandler -> {clientHandler.sendOnlineMesage();});


    }

    public void startSever(int port) throws IOException {

        serverSocket = new ServerSocket(port);
        System.out.println("Server started, listening on : " + port);
        while (true) {
            System.out.println("Waiting for a client");
            socket = serverSocket.accept();      //Blocking call
            System.out.println("New client connected");

            ClientHandler clientHandler = new ClientHandler(socket, userList, this);

            Thread thread = new Thread(clientHandler);
            thread.start();

        }
    }
}
