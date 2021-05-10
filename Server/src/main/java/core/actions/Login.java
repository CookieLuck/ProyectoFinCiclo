package core.actions;

import core.Client;
import core.Protocol;
import core.ServerCore;
import core.model.EspecieEntity;
import core.model.ProtectoraEntity;
import core.model.RazaEntity;
import org.hibernate.Session;
import session.Constants;
import utils.HibernateUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.spec.ECParameterSpec;
import java.util.List;

public class Login {

    public static void doAction(Client sock,Protocol petition){

            Protocol response = new Protocol();

            Session sessionOne = HibernateUtils.getSession();
            Query query = sessionOne.createQuery("FROM ProtectoraEntity where correo=:nombre AND contrasenia=:pass")
                    .setParameter("nombre", petition.getArgs().get("user")).setParameter("pass", petition.getArgs().get("pass"));

            List<ProtectoraEntity> protectoras = query.getResultList();

            if (protectoras.size() <= 0) {
                response.setHEADER(404);
                response.setBody("Username or password incorrect");
                sock.write(response.toString());
            } else {
                response.setHEADER(200);
                response.setBody("Login correcto");
                sock.setProtectora(protectoras.get(0));
                System.out.println(response.toString());
                sock.write(response.toString());
            }
    }


}
