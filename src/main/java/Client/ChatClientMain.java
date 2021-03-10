package Client;

import java.io.IOException;

public class ChatClientMain {

    public static void main(String[] args) throws IOException {
        String ip = "localhost";
        int port = 2345;

        if(args.length > 0){
            try{
                if (args.length == 2){
                    ip = args[0];
                    port = Integer.parseInt(args[1]);
                }else{
                    throw new IllegalArgumentException("Server not provided with the right arguments\n type: ip port");
                }
            }catch (NumberFormatException e){
                System.out.println("Illegal inputs provided when starting the server!");
                return;
            }
        }

        Client client = new Client(ip,port);
        client.connect();
    }
}
