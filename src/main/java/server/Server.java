package server;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private int port;
    public ConcurrentHashMap<String, ClientHandler> userList;

    public Server(int port) throws IOException {
        this.port=port;
        serverSocket = new ServerSocket(port);
        userList = new ConcurrentHashMap<>();
    }


    public void runServer() throws IOException {
        System.out.println("Server started, listening on : " + port);
        while (true) {
            System.out.println("Waiting for a client");
            socket = serverSocket.accept(); //Blocking call

                System.out.println("New client connected");

                ClientHandler clientHandler = new ClientHandler(socket, userList);
                Thread thread = new Thread(clientHandler);
                thread.start();



        }
    }
}
