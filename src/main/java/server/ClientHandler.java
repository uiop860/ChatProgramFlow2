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

    private ConcurrentHashMap<String, ClientHandler> userList;

    private String name = "noone"; //default name, before client chooses it

    private PrintWriter pw;
    private Scanner scanner;
    sThread serverThread;

    public ClientHandler(Socket socket, ConcurrentHashMap<String,ClientHandler> userList, Server server) throws IOException {
        this.server = server;
        this.socket = socket;
        this.userList = userList;
        serverThread= new sThread(userList);
    }



    @Override
    public void run() {
        try {
            clientHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startworker(){
        sThread stWorker = new sThread(userList);
        Thread st = new Thread(stWorker);
        st.start();
    }

    private void clientHandler() throws IOException {
        startworker();
        //System.out.println(userList.keySet());
        boolean keepRunning = true;
        boolean userConnected = false;
        pw = new PrintWriter(socket.getOutputStream(), true);
        scanner = new Scanner(socket.getInputStream());

        try{
            pw.println("Indtast CONNECT#XXXX");
            userConnected = connectClient(scanner.nextLine());

        } catch (IllegalArgumentException | NoSuchElementException e){
            e.printStackTrace();
            pw.println("CLOSE#1");
        }

        if (userConnected) {
            pw.println("Du er forbundet til chatrummet");
            try {
                while (keepRunning) {
                    String message = scanner.nextLine();
                    keepRunning = commandHandler(message);
                }
            } catch (IllegalArgumentException | NoSuchElementException e) {
                e.printStackTrace();
                pw.println("CLOSE#1");
            }
        }
        System.out.println("User disconnected");
        if (userList.containsKey(name)){
            userList.remove(name, this);
        }
        socket.close();
    }


    private boolean connectClient( String msg) {
        String[] messageSplit = msg.split("#");
        if (messageSplit.length == 2) {
            String command = messageSplit[0];
            name = messageSplit[1];
            if (command.equals("CONNECT")) {
                //Adds username to ConcurrentHashMap in Server class
                userList.put(name, this);
                serverThread.sendOnlineMessage();
                return true;
            } else {
                pw.println("CLOSE#1");
                return false;
            }
        }else{
            pw.println("CLOSE#1");
            return false;
        }
    }

    public void messageToAll(String message, String name ) {
        pw.println("MESSAGE#" + name + "#" + message);
    }


    public void sendOnlineMesage() {
        pw.print("ONLINE#");
        userList.keySet().forEach(key ->pw.print(key+","));
        pw.println();
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
                        serverThread.sendToAllUser(message, name);
                    } else {
                        serverThread.sendToSpecificUser(message, name, argument);

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
