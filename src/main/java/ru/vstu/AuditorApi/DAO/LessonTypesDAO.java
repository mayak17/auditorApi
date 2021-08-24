package ru.vstu.AuditorApi.DAO;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vstu.AuditorApi.configurations.HibernateConfiguration;
import ru.vstu.AuditorApi.entities.LessonTypes;

import java.util.List;

@Component
public class LessonTypesDAO {

    @Autowired
    HibernateConfiguration hibernateConfiguration;

    public LessonTypes findById(int id) {
        Session session = hibernateConfiguration.getMysqlSession();
        LessonTypes result = session.get(LessonTypes.class, id);
        session.close();
        return result;
    }

    public List<LessonTypes> findAll() {
        Session session = hibernateConfiguration.getMysqlSession();
        List<LessonTypes> lessonTypesList = (List<LessonTypes>) session.createQuery("from LessonTypes").list();
        session.close();
        return lessonTypesList;
    }
}
