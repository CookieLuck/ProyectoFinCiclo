import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    Runtime instance;

    Main(){
        instance = Runtime.getRuntime();
    }


    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    public void start(){
        int mb = 1024 * 1024;

        System.out.println("Server starting...\nTotal Memory: " + instance.totalMemory() / mb+"Mb");
        try {
            ServerSocket serverSocket = new ServerSocket(8036);
        } catch (IOException e) {
            System.out.println("Could not start server... ¿Is the port free?");
            e.printStackTrace();
            return;
        }
        ServerInput serverInput = new ServerInput(this);

        System.out.println("Server started.");

        while(true){

        }
    }

    public long getMemoryLeft(){
        return instance.freeMemory();
        
    }

}
