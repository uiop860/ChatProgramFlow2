package Test;


import server.Server;

import java.io.IOException;



class ClientHandlerTest {


    public static void main(String[] args) {
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
        int port = 8088;
        String logFile = "log.txt";
        Server server = new Server();
        server.startSever(port);


    }

}





