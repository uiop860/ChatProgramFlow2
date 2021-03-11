package server;

import java.io.IOException;

public class
ChatServerMain {

    public static void main(String[] args) throws IOException {
        int port = 2345;
        int capacity = 10;

        if (args.length > 0) {
            try {
                if (args.length == 1) {
                    port = Integer.parseInt(args[0]);
                } else {
                    throw new IllegalArgumentException("Server not provided with the right arguments\n type: port");
                }
            } catch (NumberFormatException ne) {
                System.out.println("Illegal inputs provided when starting the server!");
                return;
            }
        }
        Server server = new Server(port, capacity);
        server.runServer();
    }
}
