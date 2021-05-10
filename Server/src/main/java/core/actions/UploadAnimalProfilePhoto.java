package core.actions;

import com.thoughtworks.xstream.core.util.Base64Encoder;
import core.Client;
import core.Protocol;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class UploadAnimalProfilePhoto {

    public static void doAction(Client client, Protocol petition) {

        File folder = new File(Client.class.getResource("/animals/photos/" + petition.getArgs().get("id") + "/profile/").getPath() + "/photo.jpg");
        BufferedImage image = null;
        byte[] imageByte;
        try {
            Base64Encoder decoder = new Base64Encoder();
            imageByte = decoder.decode((String) petition.getBody());
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            ImageIO.write(image, "jpg", folder);
            bis.close();
            client.write(new Protocol(200,"","","","").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void doActionBypass(Client client, Protocol petition){
        File folder = new File(Client.class.getResource("/animals/photos/" + petition.getArgs().get("id") + "/profile/").getPath() + "/photo.jpg");
        BufferedImage image = null;
        byte[] imageByte;
        try {
            Base64Encoder decoder = new Base64Encoder();
            imageByte = decoder.decode((String) petition.getBody());
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            ImageIO.write(image, "jpg", folder);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
