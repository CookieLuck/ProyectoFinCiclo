package core;

import org.w3c.dom.ls.LSOutput;
import threads.ServerInput;
import utils.HibernateUtils;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;

public class ServerCore {
    DatagramPacket peticion;
    Runtime instance;
    DatagramSocket socketUDP;

    ServerCore() {
        instance = Runtime.getRuntime();
    }

    public static void main(String[] args) throws Exception {
        ServerCore serverCore = new ServerCore();
        serverCore.start();
    }

    public void start() throws Exception {

        HibernateUtils.init();
        socketUDP = new DatagramSocket(8893);
        int mb = 1024 * 1024;

        System.out.println("Server starting...\nTotal Memory: " + instance.totalMemory() / mb + "Mb");


        ServerInput serverInput = new ServerInput(this);
        serverInput.setDaemon(true);
        serverInput.start();




        while (true) {
            try {
                byte[] bufer = new byte[1000];
                peticion =
                        new DatagramPacket(bufer, bufer.length);

                socketUDP.receive(peticion);
                StringBuilder sb = new StringBuilder();


                String peticionString = (new String(bufer, StandardCharsets.UTF_8)).trim();
                sb.append(new Date()+"\n");
                sb.append("Received from: [" +
                        peticion.getAddress() + "] From port: [" +
                        peticion.getPort() + "]\nMESSAGE:\n"+peticionString+"\n");
               sb.append("//////////////////////////////////////////////////////");
                System.out.println(sb.toString());
                Protocol prol = new Protocol(peticionString);

                    Class<?> c = Class.forName("core.actions."+prol.getAction());
                    Class<?>[] paramTypes = {DatagramSocket.class, Client.class, Protocol.class};
                    Method method = c.getDeclaredMethod("doAction");
                    Client client = new Client(peticion.getAddress(),peticion.getPort());
                    MethodExecutor me = new MethodExecutor(client,socketUDP,prol,method);
                    me.start();

            } catch (SocketException e) {
                System.out.println("Socket: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                Protocol prol = new Protocol();
                prol.setHEADER(502);
                prol.setBody("Hubo un error");
                DatagramPacket respuesta =
                        new DatagramPacket(prol.toString().getBytes(StandardCharsets.UTF_8), prol.toString().getBytes(StandardCharsets.UTF_8).length,
                                peticion.getAddress(), peticion.getPort());
                socketUDP.send(respuesta);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }catch (NoClassDefFoundError e){
                Protocol prol = new Protocol();
                prol.setHEADER(502);
                prol.setBody("Hubo un error");
                DatagramPacket respuesta =
                        new DatagramPacket(prol.toString().getBytes(StandardCharsets.UTF_8), prol.toString().getBytes(StandardCharsets.UTF_8).length,
                                peticion.getAddress(), peticion.getPort());
                socketUDP.send(respuesta);
            }
        }


        //System.out.println("Server started.");

    }

    public long getMemoryLeft() {
        return instance.freeMemory();

    }

}
