package test;

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
            int port = 2345;
            Socket socket = new Socket(ip, port);
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);


            String received = scanner.nextLine();
            Assertions.assertEquals("Indtast CONNECT#XXXX", received);
            System.out.println("Test 1 passed");

            pw.println("CONNECT#Testbruger");
            String received1 = scanner.nextLine();
            Assertions.assertEquals("ONLINE#Testbruger,", received1);
            System.out.println("Test 2 passed");

            String received2 = scanner.nextLine();
            Assertions.assertEquals("Du er forbundet til chatrummet", received2);
            System.out.println("Test 3 passed");

            pw.println("SEND#*#Hej med dig");
            String received4 = scanner.nextLine();
            Assertions.assertEquals("MESSAGE#Testbruger#Hej med dig", received4);
            System.out.println("Test 4 passed");

            System.out.println("ALL test passed");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
