package Test;

import junit.framework.Assert;
import server.ClientHandler;
import server.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;




class ClientHandlerTest {

    public static void main(String[]args){
        ClientHandlerTest test1 = new ClientHandlerTest();
    }

    String ip = "localhost";
    int port = 8088;
    String logFile = "log.txt";  //Do we need this
    Server server = new Server();



    {
        try {
          Socket socket = new Socket(ip, port);
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            pw.println("CONNECT#Sebastian");
            String received = scanner.nextLine();
        


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}



