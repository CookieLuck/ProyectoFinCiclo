package cookieTrace;

import cookieTrace.forms.login.Main;
import cookieTrace.netConector.NetConector;

public class ActualMain {
    public static NetConector net;
    public static void main(String[] args) {
        Main.main(args);
    }

    public static NetConector getNet() {
        return net;
    }

    public static void setNet(NetConector net) {
        ActualMain.net = net;
    }
}
