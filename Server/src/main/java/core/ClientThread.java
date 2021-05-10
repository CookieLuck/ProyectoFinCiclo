package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketException;

import static core.ServerCore.paused;

public class ClientThread extends Thread{

    Socket socket;
    Client client;

    ClientThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        this.client = new Client(socket);
        while(true){

        if(paused){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            if (!paused) {
                String peticion;
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                peticion = input.readLine();

                System.out.println(peticion);
                Protocol prol = new Protocol(peticion);

                Class<?> c = Class.forName("core.actions." + prol.getAction());
                Class<?>[] paramTypes = {Client.class,Protocol.class};
                Method method = c.getDeclaredMethod("doAction", paramTypes);
                if (!paused) {
                        MethodExecutor me = new MethodExecutor(client, prol, method);
                        me.start();
                }


            }

        } catch (SocketException e) {
            System.out.println("Client "+this.getId()+" Disconnected");
            return;
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            Protocol prol = new Protocol();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
        }
    }
    }
}
