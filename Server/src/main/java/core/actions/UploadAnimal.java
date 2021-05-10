package core.actions;

import core.Client;
import core.Protocol;
import core.model.AnimalEntity;
import core.model.RazaEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;

import javax.persistence.Query;
import java.io.File;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UploadAnimal {

    public static void doAction(Client client, Protocol petition) {


        try {
            AnimalEntity newAnimal = new AnimalEntity();
            newAnimal.setNombre(petition.getArgs().get("name"));
            newAnimal.setDescription(petition.getArgs().get("description"));
            newAnimal.setCuidados_especiales(Boolean.parseBoolean(petition.getArgs().get("specialCare")));
            //dd-mm-YYYY
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-YYYY");
            newAnimal.setFecha_nacimiento(sdf.parse(petition.getArgs().get("birthDate")));

            Session sessionOne = HibernateUtils.getSession();
            sessionOne.getTransaction().begin();
            Query query = sessionOne.createQuery("FROM RazaEntity where id = :id").setParameter("id",Integer.parseInt(petition.getArgs().get("razeId")));

            newAnimal.setRaza((RazaEntity) query.getSingleResult());
            newAnimal.setProtectora(client.getProtectora());
            sessionOne.save(newAnimal);
            File f = new File(newAnimal.getClass().getResource("/animals/photos/").getPath()+newAnimal.getId());
            f.mkdir();

            File ff = new File(newAnimal.getClass().getResource("/animals/photos/").getPath()+newAnimal.getId()+"/profile");
            ff.mkdir();
            UploadAnimalProfilePhoto.doActionBypass(client,new Protocol(0,petition.getArgs().get("profilePhoto"),"","id",String.valueOf(newAnimal.getId())));

            sessionOne.getTransaction().commit();

            client.write(new Protocol(200,"Done!","","").toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

}
