package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;


public class ClientHandler implements Runnable {

    private Socket socket;
    private Server server;
    private MessageHandler serverhandler;
    private ConcurrentHashMap<String, ClientHandler> userList;
    private String name = "noone"; //default name, before client chooses it
    private PrintWriter pw;
    private Scanner scanner;


    public ClientHandler(Socket socket, ConcurrentHashMap<String, ClientHandler> userList, Server server) throws IOException {
        this.server = server;
        this.socket = socket;
        this.userList = userList;
        this.serverhandler = new MessageHandler(userList, this);
    }

    public void writeToClient(String message) {
        pw.println(message);
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
        boolean userConnected = false;
        pw = new PrintWriter(socket.getOutputStream(), true);
        scanner = new Scanner(socket.getInputStream());

        try {
            writeToClient("Indtast CONNECT#XXXX");
            userConnected = connectClient(scanner.nextLine());

        } catch (IllegalArgumentException | NoSuchElementException e) {
            e.printStackTrace();
            writeToClient("CLOSE#1");
        }

        if (userConnected) {
            writeToClient("Du er forbundet til chatrummet");
            try {
                while (keepRunning) {
                    String message = scanner.nextLine();
                    keepRunning = serverhandler.commandHandler(message,name);
                }
            } catch (IllegalArgumentException | NoSuchElementException e) {
                e.printStackTrace();
                writeToClient("CLOSE#1");
            }
        }
        System.out.println("User disconnected");
        if (userList.containsKey(name)) {
            userList.remove(name, this);
        }
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
                serverhandler.sendOnlineMessage();
                return true;
            } else {
                writeToClient("CLOSE#1");
                return false;
            }
        } else {
            writeToClient("CLOSE#1");
            return false;
        }
    }

    public void messageToAll(String message, String name) {
        writeToClient("MESSAGE#" + name + "#" + message);
    }
    public void sendOnlineMesage() {
        writeToClient("ONLINE#");
        userList.keySet().forEach(key -> pw.print(key + ","));
        pw.println();
    }
}
