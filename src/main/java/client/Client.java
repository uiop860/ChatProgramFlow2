package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    Socket socket;
    PrintWriter pw;
    private int port;
    private String ip;

    public Client(String ip, int port) {
        this.port = port;
        this.ip = ip;
    }

    public void connect() throws IOException, NoSuchElementException {
        socket = new Socket(ip,port);
        pw = new PrintWriter(socket.getOutputStream(),true);

        Scanner keyboard = new Scanner(System.in);
        ServerReader serverReader = new ServerReader(socket.getInputStream());
        Thread thread = new Thread(serverReader);
        thread.start();

        boolean keepRunning = true;
        while(keepRunning){
            String messageToSend = keyboard.nextLine(); //Blocking call
                pw.println(messageToSend);
        }
        socket.close();
        System.exit(0);
    }
}
