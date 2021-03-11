package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable {

    private Socket socket;
    private MessageHandler messageHandler;
    private ConcurrentHashMap<String, ClientHandler> userList;
    private String name = "noone"; //default name, before client chooses it
    private PrintWriter pw;
    private Scanner scanner;

    public ClientHandler(Socket socket, ConcurrentHashMap<String, ClientHandler> userList) throws IOException {
                this.socket = socket;
        this.userList = userList;
        this.messageHandler = new MessageHandler(userList, this);
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
            userConnected = connectClient();
        } catch (IllegalArgumentException | NoSuchElementException e) {
            e.printStackTrace();
            writeToClient("CLOSE#1");
        }

        if (userConnected) {
            writeToClient("Du er forbundet til chatrummet");
            try {
                while (keepRunning) {
                    String message = scanner.nextLine();
                    keepRunning = messageHandler.commandHandler(message,name);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                writeToClient("CLOSE#1");
            } catch (NoSuchElementException e){
                e.printStackTrace();
                writeToClient("CLOSE#2");
            }
        }
        System.out.println("User disconnected");
        if (userList.containsKey(name)) {
            userList.remove(name, this);
        }
        socket.close();
    }

    private boolean connectClient() {
     while(true){
        String msg = scanner.nextLine();
         String[] messageSplit = msg.split("#");
         if (messageSplit.length == 2) {
             String command = messageSplit[0];
             name = messageSplit[1];
             if (command.equals("CONNECT")) {
                 //Adds username to ConcurrentHashMap in Server class
                 userList.put(name, this);
                 messageHandler.sendOnlineMessage();
                 return true;
             } else {
               writeToClient("not like this"); // throw new IllegalArgumentException();
             }
         } else {
         writeToClient("Text to CONNECT#'NAME'to access");
            // throw new IllegalArgumentException();
         }

     }

    }

    public void message(String message, String name) {
        pw.println("MESSAGE#" + name + "#" + message);
    }

    public void sendOnlineMesage() {

        pw.print("\nONLINE#");
        userList.keySet().forEach(key -> pw.print(key + ","));
        pw.println();
    }

    public void writeToClient(String message) {
        pw.println(message);
    }
}
