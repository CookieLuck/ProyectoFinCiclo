package core.actions;

import com.google.gson.Gson;
import core.Client;
import core.Protocol;
import core.model.AnimalEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;

import javax.persistence.Query;
import javax.swing.*;
import java.util.List;

public class EditAnimal {

    public static void doAction(Client sock, Protocol petition){
        Protocol response = new Protocol();

        Session sessionOne = HibernateUtils.getSession();
        Query query = sessionOne.createQuery("FROM AnimalEntity where protectora = :protectora AND id = :id").setParameter("protectora",sock.getProtectora()).setParameter("id",Long.valueOf(petition.getArgs().get("id")));
        Transaction transObj = sessionOne.beginTransaction();
        List<AnimalEntity> animales = query.getResultList();

        for (AnimalEntity animal:animales){
            if(petition.getArgs().containsKey("name"))
                animal.setNombre(petition.getArgs().get("name"));
            if(petition.getArgs().containsKey("description"))
                animal.setDescription(petition.getArgs().get("description"));
            sessionOne.update(animal);
        }
        transObj.commit();

        response.setHEADER(200);
        Gson gson = new Gson();
        sock.write(response.toString());


    }


}
