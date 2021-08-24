package ru.vstu.AuditorApi.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vstu.AuditorApi.configurations.HibernateConfiguration;
import ru.vstu.AuditorApi.entities.QuizAttempts;
import ru.vstu.AuditorApi.entities.SrsAttempts;

import java.util.List;

@Component
public class QuizAttemptsDAO {
    @Autowired
    HibernateConfiguration hibernateConfiguration;

    public QuizAttempts findById(int id) {
        Session session = hibernateConfiguration.getMysqlSession();
        QuizAttempts result = session.get(QuizAttempts.class, id);
        session.close();
        return result;
    }

    public void save(QuizAttempts quizAttempts) {
        Session session = hibernateConfiguration.getMysqlSession();
        Transaction tx1 = session.beginTransaction();
        session.save(quizAttempts);
        tx1.commit();
        session.close();
    }

    public long findValueAttempts(long studentId, long lessonTypeId) {
        Session session = hibernateConfiguration.getMysqlSession();

        List<SrsAttempts> srsAttempts = (List<SrsAttempts>) session
                .createQuery("FROM QuizAttempts WHERE studentid = :param1 AND lessontypeid = :param2")
                .setParameter("param1", studentId)
                .setParameter("param2", lessonTypeId).list();
        session.close();
        return srsAttempts.size();
    }

    public QuizAttempts findMaxGradeByStudentIdAndLessonTypeId(long studentId, long lessonTypeId) {
        Session session = hibernateConfiguration.getMysqlSession();

        List<QuizAttempts> quizAttempts = (List<QuizAttempts>) session
                .createQuery("FROM QuizAttempts WHERE studentid = :param1 AND lessontypeid = :param2 ")
                .setParameter("param1", studentId)
                .setParameter("param2", lessonTypeId).list();
        session.close();
        if (quizAttempts.size() > 0) {
            return quizAttempts.get(quizAttempts.size() - 1);
        }
        return null;
    }

}
