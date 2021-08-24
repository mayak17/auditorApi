package ru.vstu.AuditorApi.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vstu.AuditorApi.configurations.HibernateConfiguration;
import ru.vstu.AuditorApi.entities.AlgorithmAttempts;

import java.util.List;

@Component
public class AlgorithmAttemptsDAO {
    @Autowired
    HibernateConfiguration hibernateConfiguration;

    public AlgorithmAttempts findById(int id) {
        Session session = this.hibernateConfiguration.getMysqlSession();
        AlgorithmAttempts result = session.get(AlgorithmAttempts.class, id);
        return result;
    }

    public void save(AlgorithmAttempts algorithmAttempts) {
        Session session = hibernateConfiguration.getMysqlSession();
        Transaction tx1 = session.beginTransaction();
        session.save(algorithmAttempts);
        tx1.commit();
        session.close();
    }

    public List<AlgorithmAttempts> findAll() {
        Session session = hibernateConfiguration.getMysqlSession();
        List<AlgorithmAttempts> algorithmAttemptsList = (List<AlgorithmAttempts>) session.createQuery("from AlgorithmAttempts").list();
        session.close();
        return algorithmAttemptsList;
    }

    public AlgorithmAttempts findLatestByStudentIdAndLessonTypeId(long studentId, long lessonTypeId) {
        Session session = hibernateConfiguration.getMysqlSession();
        List<AlgorithmAttempts> algorithmAttemptsList = (List<AlgorithmAttempts>) session
                .createQuery("from AlgorithmAttempts where studentid =: param1 and lessontypeid=:param2 ")
                .setParameter("param1",studentId)
                .setParameter("param2",lessonTypeId).list();
        session.close();
        if(algorithmAttemptsList.size()!=0)
            return algorithmAttemptsList.get(algorithmAttemptsList.size()-1);
        else
            return null;
    }

}
