package com.example.shelter.netConector;

import com.example.shelter.protocol.Protocol;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NetConector {

    private InetAddress ip;
    public Socket socket;

    public NetConector(){
        try {
            socket = new Socket("192.168.1.87",8893);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }


    public Protocol sendSimple(Protocol prol){
        PrintWriter output = null;
        BufferedReader input = null;
        try {
            output   = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        output.println(prol.toString());
        output.flush();



        try {
            String linea = input.readLine();
            System.out.println(linea);
            return new Protocol(linea);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Protocol(400,"","");
    }

    public void sendPackage(Object controller, Protocol prol, String methodOkay, String methodNotOkay, String timeOut) {
        PrintWriter output = null;
        BufferedReader input = null;
        try {
          output   = new PrintWriter(socket.getOutputStream(), true);
          input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Method okayMethod = null;
        Method notOkayMethod = null;
        Method timeOutMethod = null;
        Class clase = controller.getClass();
        try {
            Class<?>[] paramTypes = {Protocol.class};
            okayMethod = clase.getDeclaredMethod(methodOkay,paramTypes);
            notOkayMethod = clase.getDeclaredMethod(methodNotOkay,paramTypes);
            timeOutMethod = clase.getDeclaredMethod(timeOut);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(prol.toString());
            output.println(prol.toString());
            output.flush();

            String linea = input.readLine();

            System.out.println(linea);

            Protocol response = new Protocol(linea);

            if(response.getHEADER() == 200){
                Object[] objects = {response};
                okayMethod.invoke(controller,objects);
            }else{
                Object[] objects = {response};
                notOkayMethod.invoke(controller,objects);
            }

        } catch (SocketTimeoutException e) {
            try {
                timeOutMethod.invoke(controller);
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            } catch (InvocationTargetException invocationTargetException) {
                invocationTargetException.printStackTrace();
            }
        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("IO:");
            e.printStackTrace();
        }

    }

}
