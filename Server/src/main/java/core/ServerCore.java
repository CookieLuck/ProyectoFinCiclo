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
    public static boolean paused;
    boolean into;

    ServerCore() {
        instance = Runtime.getRuntime();
    }

    public static void main(String[] args) throws Exception {
        ServerCore serverCore = new ServerCore();
        serverCore.start();
    }

    public void start() throws Exception {
        HibernateUtils.getSession();
        int mb = 1024 * 1024;

        System.out.println("Server starting...\nTotal Memory: " + instance.totalMemory() / mb + "Mb");


        ServerInput serverInput = new ServerInput(this);
        serverInput.setDaemon(true);
        serverInput.start();

        ServerSocket ss = new ServerSocket(8893);
        while (true) {

            ClientThread ct = new ClientThread(ss.accept());
            ct.start();
            //System.out.println("Server started.");
        }

    }

    public long getMemoryLeft() {
        return instance.freeMemory();

    }

}
