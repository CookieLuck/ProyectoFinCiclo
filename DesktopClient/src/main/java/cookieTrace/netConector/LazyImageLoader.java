package cookieTrace.netConector;

import cookieTrace.protocol.Protocol;
import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;


public class LazyImageLoader {

    Element htmlTag;
    NetConector nc;
    WebEngine we;

    public LazyImageLoader(NetConector nc){
        this.nc = nc;
    }

    public void GetAnimalProfilePhoto(WebEngine we,Element htmlTag, Long id){
        this.htmlTag = htmlTag;
        this.we = we;
        Protocol prol = new Protocol();
        prol.setHEADER(0);
        prol.setAction("GetAnimalProfilePhoto");
        Map<String,String> args = new HashMap<String,String>();
        args.put("id",String.valueOf(id));

        prol.setArgs(args);

        success(nc.sendSimple(prol));

    }

    public void success(Protocol prol){
        Element currentHtml = htmlTag;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                currentHtml.setAttribute("src", "data:image/jpeg;base64,"+(String) prol.getBody());
                we.reload();
            }
        });

    }
    public void notOkay(Protocol prol){

    }

    public void timeOut(){

    }

}
