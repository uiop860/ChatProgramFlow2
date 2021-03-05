package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;

    public void startSever(int port) throws IOException {

        serverSocket = new ServerSocket(port);
        System.out.println("Server started, listening on : " + port);

        while (true) {
            System.out.println("Waiting for a client");
            socket = serverSocket.accept();      //Blocking call
            System.out.println("New client connected");

            ClientHandler clientHandler = new ClientHandler(socket);

            Thread thread = new Thread(clientHandler);
            thread.start();

        }
    }
}
