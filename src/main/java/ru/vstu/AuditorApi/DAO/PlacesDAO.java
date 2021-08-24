package ru.vstu.AuditorApi.DAO;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vstu.AuditorApi.configurations.HibernateConfiguration;
import ru.vstu.AuditorApi.entities.Places;

import java.util.List;

@Component
public class PlacesDAO {

    @Autowired
    private HibernateConfiguration hibernateConfiguration;

    public Places findById(int id) {
        Session session = hibernateConfiguration.getMysqlSession();
        Places result = this.hibernateConfiguration.getMysqlSession().get(Places.class, id);
        session.close();
        return result;
    }

    public List<Places> findAll() {
        Session session = hibernateConfiguration.getMysqlSession();
        List<Places> placesList = (List<Places>)  session.createQuery("from Places ").list();
        session.close();
        return placesList;
    }
}
