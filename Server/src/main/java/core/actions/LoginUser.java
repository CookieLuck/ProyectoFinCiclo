package core.actions;

import core.Client;
import core.Protocol;
import core.model.ProtectoraEntity;
import core.model.UsuarioEntity;
import org.hibernate.Session;
import utils.HibernateUtils;

import javax.persistence.Query;
import java.util.List;

public class LoginUser {

    public static void doAction(Client sock, Protocol petition){

        Protocol response = new Protocol();

        Session sessionOne = HibernateUtils.getSession();
        Query query = sessionOne.createQuery("FROM UsuarioEntity where email=:nombre AND password=:pass")
                .setParameter("nombre", petition.getArgs().get("user")).setParameter("pass", petition.getArgs().get("pass"));

        List<UsuarioEntity> protectoras = query.getResultList();

        if (protectoras.size() <= 0) {
            response.setHEADER(404);
            response.setBody("Username or password incorrect");
            sock.write(response.toString());
        } else {
            response.setHEADER(200);
            response.setBody("Login correcto");
            sock.setUser(protectoras.get(0));
            System.out.println(response.toString());
            sock.write(response.toString());
        }
    }


}
