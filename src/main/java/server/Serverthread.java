package server;



import java.util.concurrent.ConcurrentHashMap;


class sThread implements Runnable {
    String message;
    String name;
    String users;


    public sThread(ConcurrentHashMap<String, ClientHandler> userList) {
        this.userList = userList;
    }

    private ConcurrentHashMap<String, ClientHandler> userList;


    @Override
    public void run() {


        sendToAllUser(message, name);
        sendOnlineMessage();
        sendToSpecificUser(message, name, users);
    }


    public  void sendToAllUser(String message, String name) {

        userList.values().forEach(clientHandler -> {
            clientHandler.messageToAll(message, name);
        });

    }

    public  void sendOnlineMessage() {

        userList.values().forEach(clientHandler -> {
            try {
                clientHandler.sendOnlineMesage();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    public void sendToSpecificUser(String message, String name, String users) {

        String[] usersToSendTo = users.split(",");

        for (int i = 0; i < usersToSendTo.length; i++) {
            if (userList.containsKey(usersToSendTo[i])) {

                userList.get(usersToSendTo[i]).messageToAll(message, name);

            }
        }
    }
}
