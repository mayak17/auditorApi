package ru.vstu.AuditorApi.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vstu.AuditorApi.configurations.HibernateConfiguration;
import ru.vstu.AuditorApi.entities.CodestyleAttempts;

import java.util.List;

@Component
public class CodestyleAttemptsDAO {

    @Autowired
    HibernateConfiguration hibernateConfiguration;

    public CodestyleAttempts findById(int id) {

        Session session = this.hibernateConfiguration.getMysqlSession();
        CodestyleAttempts result = session.get(CodestyleAttempts.class, id);
        session.close();
        return result;
    }

    public void save(CodestyleAttempts codestyleAttempts) {
        Session session = hibernateConfiguration.getMysqlSession();
        Transaction tx1 = session.beginTransaction();
        session.save(codestyleAttempts);
        tx1.commit();
        session.close();
    }

    public List<CodestyleAttempts> findAll() {
        Session session = hibernateConfiguration.getMysqlSession();
        List<CodestyleAttempts> codestyleAttemptsList = (List<CodestyleAttempts>) session.createQuery("from CodestyleAttempts ").list();
        session.close();
        return codestyleAttemptsList;
    }

    public CodestyleAttempts findLatestByStudentIdAndLessonTypeId(long studentId, long lessonTypeId) {
        Session session = hibernateConfiguration.getMysqlSession();
        List<CodestyleAttempts> codestyleAttemptsList = (List<CodestyleAttempts>) session
                .createQuery("from CodestyleAttempts where studentid =: param1 and lessontypeid=:param2 ")
                .setParameter("param1",studentId)
                .setParameter("param2",lessonTypeId).list();
        session.close();
        if(codestyleAttemptsList.size()!=0)
            return codestyleAttemptsList.get(codestyleAttemptsList.size()-1);
        else
            return null;
    }
}
