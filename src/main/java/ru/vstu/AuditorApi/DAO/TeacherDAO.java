package ru.vstu.AuditorApi.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vstu.AuditorApi.configurations.HibernateConfiguration;
import ru.vstu.AuditorApi.entities.Teacher;

import java.util.List;

@Component
public class TeacherDAO {

    @Autowired
    HibernateConfiguration hibernateConfiguration;

    public List<Teacher> findAll() {
        Session session = this.hibernateConfiguration.getMysqlSession();
        List<Teacher> teacherList = (List<Teacher>) session.createQuery("from Teacher ").list();
        session.close();
        return teacherList;
    }
}
