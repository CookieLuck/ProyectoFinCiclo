package core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramSocket;

public class MethodExecutor extends Thread{

    private Client cliente;
    private DatagramSocket socketUDP;
    private Protocol prol;
    private Method method;

    public MethodExecutor(Client cliente, DatagramSocket socketUDP, Protocol prol, Method method){
        this.cliente = new Client(cliente.address,cliente.port);


        this.socketUDP = socketUDP;this.prol=prol;this.method = method;
    }

    @Override
    public void run() {
        try {
            Object[] objects = {socketUDP, cliente, prol};
            method.invoke(null,objects);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
