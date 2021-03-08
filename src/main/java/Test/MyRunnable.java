package Test;

import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class MyRunnable implements Runnable {


    @Override
    public void run() {
        try {
            String ip = "localhost";
            int port = 8088;
            Socket socket = new Socket(ip, port);
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);


            String received = scanner.nextLine();
            Assertions.assertEquals("Indtast CONNECT#XXXX", received);
            pw.println("CONNECT#Sebastian");
            String received1 = scanner.nextLine();
            Assertions.assertEquals("ONLINE#Sebastian,", received1);
            String received2 = scanner.nextLine();
            Assertions.assertEquals("Du er forbundet til chatrummet", received2);
            String received3 = scanner.nextLine();
            pw.println("");
            Assertions.assertEquals();


            System.out.println("test passed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
