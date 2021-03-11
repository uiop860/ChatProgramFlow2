package Client;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerReader implements Runnable {

    Scanner scanner;

    public ServerReader(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public void run() {
        while(true){
            try{
                String message = scanner.nextLine();
                if (message.equals("CLOSE#0")){
                    System.out.println("Normal close from server");
                    System.exit(0);
                } else if (message.equals("CLOSE#1")){
                    System.out.println("Server received illegal input");
                    System.exit(1);
                }else if (message.equals("CLOSE#2")){
                    System.out.println("Specified user was not found");
                    System.exit(2);
                } else {
                    System.out.println(message);
                }
            }catch (NoSuchElementException ignored){
            }
        }
    }
}
