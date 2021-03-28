package threads;

import core.ServerCore;

public class ClientProcessorThread extends Thread{

    ServerCore serverCore;

    ClientProcessorThread(ServerCore serverCore){
        this.serverCore = serverCore;
    }

    @Override
    public void run() {

    }
}
