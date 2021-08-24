package ru.vstu.AuditorApi.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vstu.AuditorApi.configurations.HibernateConfiguration;
import ru.vstu.AuditorApi.entities.ModTesting;

import java.util.List;

@Component
public class ModTestingDAO {

    @Autowired
    HibernateConfiguration hibernateConfiguration;

    public ModTesting findById(int id) {
        Session session = hibernateConfiguration.getMysqlSession();
        ModTesting result = session.get(ModTesting.class, id);
        session.close();
        return result;
    }

    public void save(ModTesting modTesting) {
        Session session = hibernateConfiguration.getMysqlSession();
        Transaction tx1 = session.beginTransaction();
        session.save(modTesting);
        tx1.commit();
        session.close();
    }

    public List<ModTesting> findAll() {
        Session session = hibernateConfiguration.getMysqlSession();

        List<ModTesting> modTestingList = (List<ModTesting>) session.createQuery("from ModTesting ").list();
        session.close();
        return modTestingList;
    }

    public ModTesting findLastByAttemptId(int attemptId) {
        Session session = hibernateConfiguration.getMysqlSession();

        List<ModTesting> modTestingList = (List<ModTesting>) session
                .createQuery("from ModTesting where modattemptid=:paramName")
                .setParameter("paramName", attemptId)
                .list();
        session.close();
        return modTestingList.get(modTestingList.size() - 1);
    }

    public long countCurrentTesting(long attemptId) {
        Session session = hibernateConfiguration.getMysqlSession();

        List<ModTesting> modTestingList = (List<ModTesting>) session
                .createQuery("from ModTesting where modattemptid=:paramName")
                .setParameter("paramName", attemptId)
                .list();
        session.close();
        return (long)modTestingList.size();
    }

}
