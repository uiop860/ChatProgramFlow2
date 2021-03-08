package Test;


import org.junit.jupiter.api.Test;
import server.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static junit.framework.Assert.assertEquals;





class ClientHandlerTest {




    public static void main(String[]args){
        try {
            Runtestserver();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void Runtestserver() throws IOException {
        MyRunnable testrunnable = new MyRunnable();
        Thread testThread = new Thread(testrunnable);
        testThread.start();
        String ip = "localhost";
        int port = 8088;
        String logFile = "log.txt";
        Server server = new Server();
        server.startSever(port, ip);



    }

}




