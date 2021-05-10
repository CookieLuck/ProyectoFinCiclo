package core.actions;

import com.google.gson.Gson;
import core.Client;
import core.Protocol;
import core.model.AnimalEntity;
import core.model.EspecieEntity;
import org.hibernate.Session;
import utils.HibernateUtils;

import javax.persistence.Query;
import java.util.List;

public class GetEspecies {

    public static void doAction(Client sock, Protocol petition){

        Protocol response = new Protocol();

        Session sessionOne = HibernateUtils.getSession();
        Query query = sessionOne.createQuery("FROM EspecieEntity");


        response.setHEADER(200);

        Gson gson = new Gson();
        List<EspecieEntity> especieEntities = query.getResultList();

        response.setBody(especieEntities);
        System.out.println(gson.toJson(especieEntities));
        sock.write(response.toString());


    }

}
