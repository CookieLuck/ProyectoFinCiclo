package core;

import threads.ServerInput;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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

        ServerInput serverInput = new ServerInput(this);
        serverInput.setDaemon(true);
        serverInput.start();

        try {

            DatagramSocket socketUDP = new DatagramSocket(8893);
            byte[] bufer = new byte[1000];

            while (true) {
                DatagramPacket peticion =
                        new DatagramPacket(bufer, bufer.length);

                System.out.println("waiting for petition");
                socketUDP.receive(peticion);

                System.out.print("Datagrama recibido del host: " +
                        peticion.getAddress());
                System.out.println(" desde el puerto remoto: " +
                        peticion.getPort());

                DatagramPacket respuesta =
                        new DatagramPacket("test".getBytes(StandardCharsets.UTF_8), "test".length(),
                                peticion.getAddress(), peticion.getPort());

                socketUDP.send(respuesta);
            }

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }



        //System.out.println("Server started.");

    }

    public long getMemoryLeft(){
        return instance.freeMemory();

    }

}
