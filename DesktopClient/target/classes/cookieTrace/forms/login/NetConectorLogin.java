package cookieTrace.forms.login;

import java.io.IOException;
import java.net.*;

public class NetConectorLogin extends Thread{
    private InetAddress ip;
    private DatagramSocket socket;
    LoginController loginController;
    boolean sendLogin;
    String messaje;

    @Override
    public void run() {
        messaje = "";
        while(true){
            if(sendLogin){
                try {
                    if (messaje.getBytes().length >= 1000) {
                        loginController.errormsg("ERROR");
                        sendLogin = false;
                    }
                    if(sendLogin) {
                        byte[] m = messaje.getBytes();
                        DatagramPacket mensajeSalida =
                                new DatagramPacket(m, m.length, ip, 8893);
                        socket.send(mensajeSalida);

                        byte[] bufer = new byte[1000];
                        String linea;

                        DatagramPacket mensajeEntrada =
                                new DatagramPacket(bufer, bufer.length);
                        socket.receive(mensajeEntrada);
                        linea = new String(mensajeEntrada.getData(), 0, mensajeEntrada.getLength());
                        loginController.errormsg(linea);
                        sendLogin = false;
                    }


                } catch (SocketException e) {
                    System.out.println("Socket:" + e.getMessage());
                } catch (IOException e) {
                    System.out.println("IO:" + e.getMessage());
                }

            }
        }
    }

    NetConectorLogin(LoginController controller){
        this.loginController = controller;
        try {
            ip = InetAddress.getByName("localHost");
            socket = new DatagramSocket(8894);

        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
    }


    public void sendLogin(String messaje) {
        this.messaje = messaje;
        this.sendLogin = true;
    }

}
