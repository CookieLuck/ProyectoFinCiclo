package cookieTrace.forms.login;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Conectioncloser extends Thread{

    DatagramSocket socket;

    public Conectioncloser(DatagramSocket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;

        }
    }
}
