package Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static junit.framework.Assert.assertEquals;

public class MyRunnable implements Runnable {


        @Override
        public void run() {
                try {
                        String ip = "localhost";
                        int port = 8088;
                        Socket socket = new Socket(ip, port);
                        Scanner scanner = new Scanner(socket.getInputStream());
                        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

                        //pw.println("CONNECT#Sebastian");
                        String received = scanner.nextLine();
                        assertEquals("Indtast CONNECT#XXXX", received);
                        pw.println("CONNECT#Sebastian");
                        String received1 = scanner.nextLine();
                        assertEquals("Du er forbundet til chatrummet",received1);
                        



                        System.out.println("test passed");
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }



        }
