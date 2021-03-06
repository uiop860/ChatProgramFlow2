package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;

    public void sendToSpecificUsers(String message, String[] users){


    }

    public void sendToAllUser(String message){


    }

    public void startSever(int port,String ip) throws IOException {

        serverSocket = new ServerSocket(port);
        System.out.println("Server started, listening on : " + port);

        ConcurrentHashMap<String,String> userList = new ConcurrentHashMap<>(10);


        while (true) {
            System.out.println("Waiting for a client");
            socket = serverSocket.accept();      //Blocking call
            System.out.println("New client connected");

            ClientHandler clientHandler = new ClientHandler(socket,ip,userList,this);

            Thread thread = new Thread(clientHandler);
            thread.start();

        }
    }
}
