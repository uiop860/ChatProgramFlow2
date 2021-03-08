package Test;


import server.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static junit.framework.Assert.assertEquals;


class Runnabletest implements Runnable{
    ClientHandlerTest c1test = new ClientHandlerTest();

    @Override
    public void run() {
        try {
            c1test.test();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


class ClientHandlerTest {


    public static void main(String[]args){
        try {
            test();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void test() throws IOException {
    String ip = "localhost";
    int port = 8088;
    String logFile = "log.txt";
    Server server = new Server();

   Thread testThread = new Thread();




        try {
            Socket socket = new Socket(ip, port);
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            pw.println("CONNECT#Sebastian");
            String received = scanner.nextLine();
            assertEquals("Du er forbundet til chatrummet", received);
            pw.println();


            System.out.println("test passed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




