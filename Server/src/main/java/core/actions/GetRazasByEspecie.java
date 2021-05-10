package core.actions;

import com.google.gson.Gson;
import core.Client;
import core.Protocol;
import core.model.AnimalEntity;
import core.model.RazaEntity;
import org.hibernate.Session;
import utils.HibernateUtils;

import javax.persistence.Query;
import java.util.List;

public class GetRazasByEspecie {
    public static void doAction(Client sock, Protocol petition) {

        Protocol response = new Protocol();
        int value = Integer.valueOf(petition.getArgs().get("id"));
        Session sessionOne = HibernateUtils.getSession();
        Query query = sessionOne.createQuery("SELECT r FROM RazaEntity r, EspecieEntity e where r.especie = e and e.id = :especie").setParameter("especie",Integer.valueOf(petition.getArgs().get("id")));

        List<RazaEntity> razas = query.getResultList();
        response.setHEADER(200);
        Gson gson = new Gson();
        response.setBody(razas);
        System.out.println(gson.toJson(razas));
        sock.write(response.toString());




    }
}
