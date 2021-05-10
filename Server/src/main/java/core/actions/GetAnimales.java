package core.actions;

import com.google.gson.Gson;
import core.Client;
import core.Protocol;
import core.ServerCore;
import core.model.AnimalEntity;
import org.hibernate.Session;
import session.Constants;
import utils.HibernateUtils;

import javax.persistence.Query;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

public class GetAnimales {

    public static void doAction(Client sock, Protocol petition){

        Protocol response = new Protocol();

        Session sessionOne = HibernateUtils.getSession();
        Query query = sessionOne.createQuery("FROM AnimalEntity where protectora = :protectora").setParameter("protectora",sock.getProtectora());


        response.setHEADER(200);
        Gson gson = new Gson();
        List<AnimalEntity> animales = query.getResultList();

        response.setBody(animales);
        System.out.println(gson.toJson(animales));
            sock.write(response.toString());


    }

}
