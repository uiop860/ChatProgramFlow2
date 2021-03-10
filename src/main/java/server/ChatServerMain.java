package server;

import java.io.IOException;

public class ChatServerMain {

    public static void main(String[] args) throws IOException {
        int port = 2345;
        Server server = new Server();

        if(args.length > 0){
            try {
                if (args.length == 1){
                    port = Integer.parseInt(args[0]);
                }
                else {
                    throw new IllegalArgumentException("Server not provided with the right arguments\n type: ip port log");
                }
            } catch (NumberFormatException ne) {
                System.out.println("Illegal inputs provided when starting the server!");
                return;
            }

        }
        server.startSever(port);
    }
}
