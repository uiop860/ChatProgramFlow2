package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable {

    private Socket socket;
    private PrintWriter pw;
    private Scanner scanner;
    private String ip;
    private ConcurrentHashMap<String,String> userList;
    private Server server;

    public ClientHandler(Socket socket, String ip, ConcurrentHashMap<String,String> userList,Server server) throws IOException {
        this.socket = socket;
        this.ip = ip;
        this.userList = userList;
        this.server = server;
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
            try {
                keepRunning = commandHandler(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pw.println("Du har lukket forbindelsen til chatrummet");
    }

    private boolean commandHandler(String msg) throws InterruptedException {

        String[] messageSplit = msg.split("#");

        if (messageSplit.length == 1){
            String command = messageSplit[0];
            switch (command){
                case "CLOSE":
                    //Stops while loop and closes connection
                    return false;
                default:
                    throw new IllegalArgumentException("Sent request does not obey protocol");
            }

        }else if (messageSplit.length == 2){
            String command = messageSplit[0];
            String token = messageSplit[1];
            switch (command){
                case "CONNECT":
                    //Adds username to ArrayBlockingQueue in Server class
                    userList.put(ip,token);
                    break;

                default:
                    throw new IllegalArgumentException("Sent request does not obey protocol");
            }

        }else if (messageSplit.length == 3){
            String command = messageSplit[0];
            String argument = messageSplit[1];
            String message = messageSplit[2];

            switch (command){
                case "SEND":
                    if(!message.equals("*")){
                        String[] users = argument.split(",");
                        server.sendToSpecificUsers(message,users);

                    }else {
                        server.sendToAllUser(message);
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Sent request does not obey protocol");
            }
        }

        return true;
    }
}
