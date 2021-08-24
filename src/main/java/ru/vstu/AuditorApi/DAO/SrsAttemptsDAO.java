package ru.vstu.AuditorApi.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vstu.AuditorApi.configurations.HibernateConfiguration;
import ru.vstu.AuditorApi.entities.SrsAttempts;

import java.util.List;

@Component
public class SrsAttemptsDAO {
    @Autowired
    private HibernateConfiguration hibernateConfiguration;

    public SrsAttempts findById(int id) {
        Session session = hibernateConfiguration.getMysqlSession();
        SrsAttempts result = session.get(SrsAttempts.class, id);
        session.close();
        return result;
    }

    public void save(SrsAttempts srsAttempts) {
        Session session = hibernateConfiguration.getMysqlSession();
        Transaction tx1 = session.beginTransaction();
        session.save(srsAttempts);
        tx1.commit();
        session.close();
    }

    public int findValueAttempts(long studentId, long variantId) {
        Session session = hibernateConfiguration.getMysqlSession();

        List<SrsAttempts> srsAttempts = (List<SrsAttempts>) session
                .createQuery("FROM SrsAttempts WHERE studentid = :param1 AND variantid = :param2 and formallycorrect=1")
                .setParameter("param1", studentId)
                .setParameter("param2", variantId).list();
        session.close();
        if(srsAttempts.size()>0)
            return srsAttempts.size();
        else
            return 0;
    }

    public SrsAttempts findLatestByStudentIdAndVariantId(long studentId, long variantId) {
        Session session = hibernateConfiguration.getMysqlSession();

        List<SrsAttempts> srsAttemptsList = (List<SrsAttempts>) session
                .createQuery("from SrsAttempts where studentid=: param1 and variantid =:param2")
                .setParameter("param1", studentId)
                .setParameter("param2", variantId).list();
        session.close();
        if (srsAttemptsList.size() > 0) {
            return srsAttemptsList.get(srsAttemptsList.size()-1);
        }
        return null;
    }
    public boolean isSrsComleted(long studentId,long variantId){
        Session session = hibernateConfiguration.getMysqlSession();

        List<SrsAttempts> srsAttemptsList = (List<SrsAttempts>) session
                .createQuery("from SrsAttempts where studentid=: param1 and variantid =:param2 and alltestspassed=1")
                .setParameter("param1", studentId)
                .setParameter("param2", variantId).list();
        session.close();
        if(srsAttemptsList.size()>0)
            return true;
        else
            return false;
    }
}
