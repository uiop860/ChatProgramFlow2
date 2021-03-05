package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private Socket socket;
    private PrintWriter pw;
    private Scanner scanner;
    private String ip;

    public ClientHandler(Socket socket, String ip) throws IOException {
        this.socket = socket;
        this.ip = ip;
        this.pw = new PrintWriter(socket.getOutputStream());
        this.scanner = new Scanner(socket.getInputStream());
    }

    @Override
    public void run() {
        clientHandler();

    }

    private void clientHandler(){
        boolean keepRunning = true;
        pw.println("Du er forbundet til chatrummet");


        while(keepRunning) {

            String message = scanner.nextLine();
            keepRunning = commandHandler(message);

        }
        pw.println("Du har lukket forbindelsen til chatrummet");
    }

    private boolean commandHandler(String msg){

        String[] messageSplit = msg.split("#");

        if (messageSplit.length == 1){

        }else if (messageSplit.length == 2){

        }else if (messageSplit.length == 3){

        }

        return true;
    }
}
