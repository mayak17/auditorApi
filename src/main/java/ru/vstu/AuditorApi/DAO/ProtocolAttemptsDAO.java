package ru.vstu.AuditorApi.DAO;



import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vstu.AuditorApi.configurations.HibernateConfiguration;
import ru.vstu.AuditorApi.entities.ProtocolAttempts;
import java.util.List;

@Component
public class ProtocolAttemptsDAO {
    @Autowired
    HibernateConfiguration hibernateConfiguration;

    public ProtocolAttempts findById(int id) {
        Session session = hibernateConfiguration.getMysqlSession();
        ProtocolAttempts result = session.get(ProtocolAttempts.class, id);
        session.close();
        return result;
    }

    public void save(ProtocolAttempts protocolAttempts) {
        Session session = hibernateConfiguration.getMysqlSession();
        Transaction tx1 = session.beginTransaction();
        session.save(protocolAttempts);
        tx1.commit();
        session.close();
    }

    public List<ProtocolAttempts> findAll() {
        Session session = hibernateConfiguration.getMysqlSession();

        List<ProtocolAttempts> protocolAttemptsList = (List<ProtocolAttempts>) session.createQuery("from ProtocolAttempts ").list();
        session.close();
        return protocolAttemptsList;
    }

    public ProtocolAttempts findLatestByStudentIdAndLessonTypeId(long studentId, long lessonTypeId) {
        Session session = hibernateConfiguration.getMysqlSession();

        List<ProtocolAttempts> protocolAttemptsList = (List<ProtocolAttempts>) session
                .createQuery("from ProtocolAttempts where studentid=: param1 and lessontypeid=:param2")
                .setParameter("param1",studentId)
                .setParameter("param2",lessonTypeId).list();
        session.close();
        if(protocolAttemptsList.size()!=0)
            return protocolAttemptsList.get(protocolAttemptsList.size()-1);
        else
            return null;
    }
    public List testResult(long studentId, long lessonTypeId){
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT * FROM vg_lab_details where studentid = :param1 and lessontypeid = :param2")
                .setParameter("param1", studentId)
                .setParameter("param2", lessonTypeId);
        List result =query.list();
        session.close();
        return result;
    }
}
