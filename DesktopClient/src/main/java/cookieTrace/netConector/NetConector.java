package cookieTrace.netConector;

import cookieTrace.forms.login.LoginController;
import cookieTrace.protocol.Protocol;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class NetConector {

    private InetAddress ip;
    public DatagramSocket socket;

    public NetConector(){
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(2000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendPackage(Object controller,Protocol prol, String methodOkay, String methodNotOkay, String timeOut) {
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
            if (prol.toString().getBytes(StandardCharsets.UTF_8).length >= 1000) {
                Protocol protol = new Protocol();
                protol.setHEADER(502);
                protol.setBody("No puedes enviar un packete tan grande");
                Object[] objects = {protol};
                notOkayMethod.invoke(clase,objects);
            }
            ip = InetAddress.getByName("localHost");

            byte[] tosend = prol.toString().getBytes(StandardCharsets.UTF_8);
            System.out.println(new String(tosend));
            DatagramPacket mensajeSalida =
                    new DatagramPacket(tosend, tosend.length, ip, 8893);
            socket.send(mensajeSalida);

            byte[] bufer = new byte[1000];
            String linea;

            DatagramPacket mensajeEntrada =
                    new DatagramPacket(bufer, bufer.length);
            socket.receive(mensajeEntrada);
            linea = new String(bufer, StandardCharsets.UTF_8).trim();
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
            System.out.println("IO:" + e.getMessage());
        }

    }

}
