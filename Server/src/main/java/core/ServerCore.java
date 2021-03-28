package core;

import threads.ServerInput;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCore {

    Runtime instance;

    ServerCore(){
        instance = Runtime.getRuntime();
    }


    public static void main(String[] args) {
        ServerCore serverCore = new ServerCore();
        serverCore.start();
    }

    public void start(){
        int mb = 1024 * 1024;

        System.out.println("Server starting...\nTotal Memory: " + instance.totalMemory() / mb+"Mb");
        ServerSocket serverSocket;
        try {

            serverSocket = new ServerSocket(8036);
            ServerInput serverInput = new ServerInput(this);
            serverInput.setDaemon(true);
            serverInput.start();

        } catch (IOException e) {

            System.out.println("Could not start server... ¿Is the port free?");
            e.printStackTrace();
            return;

        }



        System.out.println("Server started.");

        while(true){

            try {
                Socket s = serverSocket.accept();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public long getMemoryLeft(){
        return instance.freeMemory();

    }

}
