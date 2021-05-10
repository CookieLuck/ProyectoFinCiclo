package core.actions;

import com.thoughtworks.xstream.core.util.Base64Encoder;
import core.Client;
import core.Protocol;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class GetAnimalProfilePhoto {

    public static void doAction(Client client, Protocol petition){

        File folder = new File(Client.class.getResource("/animals/photos/"+petition.getArgs().get("id")+"/profile/").getPath()+"/");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                try {
                    Base64Encoder bs = new Base64Encoder();
                    String base64 = new String(bs.encode(Files.readAllBytes(file.toPath())));
                    Protocol prol = new Protocol();
                    prol.setHEADER(200);
                    prol.setBody(base64);
                    System.out.println(base64);
                    client.write(prol.toString());
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
