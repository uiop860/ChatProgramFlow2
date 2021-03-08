package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable {

    private Socket socket;
    private String ip;
    private ConcurrentHashMap<String,String> userList;
    private Server server;
    private PrintWriter pw;
    private Scanner scanner;

    public ClientHandler(Socket socket, String ip, ConcurrentHashMap<String,String> userList,Server server) throws IOException {
        this.socket = socket;
        this.ip = ip;
        this.userList = userList;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            clientHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void clientHandler() throws IOException {
        boolean keepRunning = true;
        boolean userConnected;
        pw = new PrintWriter(socket.getOutputStream(), true);
        scanner = new Scanner(socket.getInputStream());

        pw.println("Indtast CONNECT#XXXX");
        userConnected = connectClient(scanner.nextLine());

        if (userConnected) {
            pw.println("Du er forbundet til chatrummet");
            try {
                while (keepRunning) {
                    String message = scanner.nextLine();
                    keepRunning = commandHandler(message);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                System.out.println("Illegal argument from user");
            }
        }
        socket.close();
    }

    private boolean connectClient(String msg){
        String[] messageSplit = msg.split("#");

        if (messageSplit.length == 2){
            String command = messageSplit[0];
            String token = messageSplit[1];
            if (command.equals("CONNECT")) {
                //Adds username to ArrayBlockingQueue in Server class
                userList.put(ip, token);
                return true;
            }else{
                throw new IllegalArgumentException("Sent request does not obey protocol");
            }
        }
        return false;
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
