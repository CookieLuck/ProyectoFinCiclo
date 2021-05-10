package core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramSocket;
import java.net.Socket;

public class MethodExecutor extends Thread{

    private Client cliente;
    private Socket socket;
    private Protocol prol;
    private Method method;

    public MethodExecutor(Client cliente, Protocol prol, Method method){
        this.cliente = cliente;
        this.socket = socket;this.prol=prol;this.method = method;
    }

    @Override
    public void run() {
        try {
            Object[] objects = {cliente, prol};
            method.invoke(null,objects);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
