package server;


import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class MessageHandler {
    private ConcurrentHashMap<String, ClientHandler> userList;
    private ClientHandler myClientHandler;

    public MessageHandler(ConcurrentHashMap<String, ClientHandler> userList, ClientHandler clientHandler) {
        this.userList = userList;
        this.myClientHandler = clientHandler;
    }

    public boolean commandHandler(String msg, String myName) {
        String[] messageSplit = msg.split("#");

        if (messageSplit.length == 1) {
            String command = messageSplit[0];
            switch (command) {
                case "CLOSE":
                    myClientHandler.writeToClient("CLOSE#0");
                    return false;
                default:
                    throw new IllegalArgumentException();
            }
        } else if (messageSplit.length == 3) {
            String command = messageSplit[0];
            String argument = messageSplit[1];
            String message = messageSplit[2];

            switch (command) {
                case "SEND":
                    if (argument.equals("*")) {
                        sendToAllUser(message, myName);
                    } else {
                        sendToSpecificUser(message, myName, argument);
                    }
                    break;
                default:

                    throw new IllegalArgumentException();

            }
        }
        return true;
    }

    public void sendToAllUser(String message, String name) {
        userList.values().forEach(clientHandler -> {
            clientHandler.messageToAll(message, name);
        });
    }

    public void sendOnlineMessage() {
        userList.values().forEach(clientHandler -> {
            clientHandler.sendOnlineMesage();
        });
    }

    public void sendToSpecificUser(String message, String name, String user) {
        if (userList.containsKey(user)){
            userList.get(user).messageToAll(message, name);
        } else {
            throw new NoSuchElementException();
        }
    }
}
