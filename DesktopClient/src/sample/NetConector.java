package sample;

import sun.nio.ch.Net;

import java.io.IOException;
import java.net.*;

public class NetConector {
    private static InetAddress ip;
    private static DatagramSocket socket;
    static {
        try {
            NetConector.ip = InetAddress.getByName("192.168.1.87");
            socket = new DatagramSocket(8894);

        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
    }


    public static String sendMessaje(String messaje) {
        try {

            if (messaje.getBytes().length >= 1000) {
                return null;
            }
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
            return linea;

        } catch (SocketException e) {
            System.out.println("Socket:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        }
        return null;
    }

}
