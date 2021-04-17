package cookieTrace.netConector;

import cookieTrace.protocol.Protocol;

public class NetProcess extends Thread{
    Object context;
    NetConector nc;
    Protocol prol;
    String okay;
    String notOkay;
    String timeOut;

    public NetProcess(Object context, NetConector nc, Protocol prol, String okay, String notOkay, String timeOut) {
        this.context = context;
        this.nc = nc;
        this.prol = prol;
        this.okay = okay;
        this.notOkay = notOkay;
        this.timeOut = timeOut;
        this.start();
    }

    @Override
    public void run() {
        nc.sendPackage(context,prol,okay,notOkay,timeOut);
    }
}
