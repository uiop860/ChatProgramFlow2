package server;

import java.io.IOException;

public class ChatServerMain {

    //Call server with arguments like this: 0.0.0.0 8088 logfile.log
    public static void main(String[] args) throws IOException {
        String ip = "localhost";
        int port = 8088;
        Server server = new Server();

        try {
            if (args.length == 3) {
                ip = args[0];
                port = Integer.parseInt(args[1]);

            }
            if (args.length == 2) {
                ip = args[0];
                port = Integer.parseInt(args[1]);
            } else {
                throw new IllegalArgumentException("Server not provided with the right arguments\n type: ip port log");
            }
        } catch (NumberFormatException ne) {
            System.out.println("Illegal inputs provided when starting the server!");
            return;
        }
        server.startSever(port);
    }
}
