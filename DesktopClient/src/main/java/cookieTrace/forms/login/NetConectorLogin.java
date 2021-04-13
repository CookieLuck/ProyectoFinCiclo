package cookieTrace.forms.login;

import java.io.IOException;
import java.net.*;

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
            this.socket = new DatagramSocket(8894);
            socket.setSoTimeout(2000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }


    public void sendLogin(String messaje, Conectioncloser cc) {
        this.messaje = messaje;
        this.sendLogin = true;

            try {
                if (messaje.getBytes().length >= 1000) {
                    loginController.errormsg("ERROR");
                }
                    ip = InetAddress.getByName("localHost");
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
                    cc.interrupt();
                    sendLogin = false;

            } catch (SocketTimeoutException e) {
                loginController.errormsg("Oops... Something went wrong (Err 504) ");
                cc.interrupt();
                return;
            } catch (IOException e) {
                System.out.println("IO:" + e.getMessage());
            }

        }


}
