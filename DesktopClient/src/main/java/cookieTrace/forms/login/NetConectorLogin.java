package cookieTrace.forms.login;

import cookieTrace.protocol.Protocol;

import java.io.IOException;
import java.io.Writer;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class NetConectorLogin{
    private InetAddress ip;
    public DatagramSocket socket;
    LoginController loginController;
    boolean sendLogin;
    String messaje;
    boolean recieved;

    NetConectorLogin(LoginController controller){
        this.loginController = controller;
        try {
            this.socket = new DatagramSocket();
            socket.setSoTimeout(2000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }


    public void sendLogin(String usuario,String constrasenia) {
        this.sendLogin = true;

            try {
                if (usuario.getBytes().length+constrasenia.getBytes().length >= 1000) {
                    loginController.errormsg("ERROR");
                }
                    ip = InetAddress.getByName("localHost");

                Protocol pro = new Protocol();
                Map<String,String> args = new HashMap<String,String>();
                args.put("user",usuario);
                args.put("pass",constrasenia);
                pro.setHEADER(0);
                pro.setAction("Login");
                pro.setArgs(args);

                byte[] tosend = pro.toString().getBytes(StandardCharsets.UTF_8);
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
                    Protocol prol = new Protocol(linea);


                    System.out.println(prol.toString());
                    loginController.errormsg(prol.getBody());
                    sendLogin = false;

            } catch (SocketTimeoutException e) {
                loginController.errormsg("Oops... Something went wrong (Err 504) ");
                return;
            } catch (IOException e) {
                System.out.println("IO:" + e.getMessage());
            }

        }


}
