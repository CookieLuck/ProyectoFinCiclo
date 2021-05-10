package core.actions;

import com.google.gson.Gson;
import core.Client;
import core.Protocol;
import core.model.AnimalEntity;
import org.hibernate.Session;
import utils.HibernateUtils;

import javax.persistence.Query;
import java.util.List;

public class GetAnimalesAdoptados {

    public static void doAction(Client client, Protocol petition){
        Protocol response = new Protocol();

        Session sessionOne = HibernateUtils.getSession();
        Query query = sessionOne.createQuery("FROM AnimalEntity where protectora = :protectora").setParameter("protectora",client.getProtectora());


        response.setHEADER(200);
        Gson gson = new Gson();
        List<AnimalEntity> animales = query.getResultList();

        response.setBody(animales);
        System.out.println(gson.toJson(animales));
        client.write(response.toString());
    }

}
