package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable {

    private Socket socket;
    private ConcurrentHashMap<String,ClientHandler> userList;
    private Server server;
    public String name;
    private PrintWriter pw;
    private Scanner scanner;

    public ClientHandler(Socket socket, ConcurrentHashMap<String,ClientHandler> userList, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.userList = userList;
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
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                pw.println("CLOSE#1");
            }
        }
        userList.remove(name, this);
        socket.close();
    }

    private boolean connectClient(String msg) {
        String[] messageSplit = msg.split("#");

        if (messageSplit.length == 2) {
            String command = messageSplit[0];
            name = messageSplit[1];
            if (command.equals("CONNECT")) {
                //Adds username to ConcurrentHashMap in Server class
                userList.put(name, this);
                server.sendOnlineMessage();
                return true;
            } else {
                pw.println("CLOSE#1");
                throw new IllegalArgumentException("CLOSE#1");
            }
        }
        return false;
    }

    public void messageToAll(String message, String senderName) {
        pw.println("MESSAGE#" + senderName + "#" + message);
    }

    /*public void messageToSpecific(String message){


    }*/


    public void sendOnlineMesage() {
        pw.print("ONLINE#");
        userList.keySet().forEach(key ->pw.print(key+","));
        pw.println();
        Serverthread stWorker = new Serverthread(userList);
        Thread st = new Thread(stWorker);
        st.start();
    }

    private boolean commandHandler(String msg) {

        String[] messageSplit = msg.split("#");

        if (messageSplit.length == 1) {
            String command = messageSplit[0];
            switch (command) {
                case "CLOSE":
                    //Stops while loop and closes connection
                    return false;
                default:
                    pw.println("CLOSE#1");
                    throw new IllegalArgumentException("CLOSE#1");
            }

        } else if (messageSplit.length == 3) {
            String command = messageSplit[0];
            String argument = messageSplit[1];
            String message = messageSplit[2];

            switch (command) {
                case "SEND":
                    if (argument.equals("*")) {
                        server.sendToAllUser(message, name);
                    } else {
                        /*String[] parts = argument.split(",");*/
                        /*if (parts.length == 0) {*/
                            server.sendToSpecificUser(message, name, argument);
                        /*} else {
                            server.sendToSpecificUsers(message, name);
                        }*/
                    }
                    break;
                default:
                    pw.println("CLOSE#1");
                    throw new IllegalArgumentException("CLOSE#1");
            }
        }
        return true;
    }
}
