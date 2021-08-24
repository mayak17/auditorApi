package ru.vstu.AuditorApi.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vstu.AuditorApi.configurations.HibernateConfiguration;
import ru.vstu.AuditorApi.entities.Lessons;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class LessonsDAO {

    @Autowired
    HibernateConfiguration hibernateConfiguration;

    public Lessons findById(int id) {
        Session session = hibernateConfiguration.getMysqlSession();
        Lessons result = session.get(Lessons.class, id);
        session.close();
        return result;
    }

    public long save(Lessons lessons) {
        Session session = hibernateConfiguration.getMysqlSession();
        Transaction tx1 = session.beginTransaction();
        session.save(lessons);
        tx1.commit();
        long id=lessons.getId();
        session.close();
        return id;
    }

    public List<Lessons> findAll() {
        Session session = hibernateConfiguration.getMysqlSession();
        List<Lessons> lessonsList = (List<Lessons>) session.createQuery("from Lessons ").list();
        session.close();
        return lessonsList;
    }
    public long isLessonStarted(Lessons lessons){
        Session session = hibernateConfiguration.getMysqlSession();
        List<Long> lessonsList = (List<Long>) session
                .createQuery("select id from Lessons where lessontypeid =:param1 and groupid =:param2 and teacherid=:param3 and placeid =:param4 and dateoflesson =:param5")
                .setParameter("param1",lessons.getLessontypeid())
                .setParameter("param2",lessons.getGroupid())
                .setParameter("param3",lessons.getTeacherid())
                .setParameter("param4",lessons.getPlaceid())
                .setParameter("param5",lessons.getDateoflesson())
                .list();
        session.close();
        if(lessonsList.size()!=0)
            return lessonsList.get(0);
        else
            return 0;
    }
}
