package ru.vstu.AuditorApi.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vstu.AuditorApi.configurations.HibernateConfiguration;
import ru.vstu.AuditorApi.entities.StudentGroups;

import java.util.List;

@Component
public class StudentGroupsDAO {

    @Autowired
    private HibernateConfiguration hibernateConfiguration;

    public StudentGroups findById(int id) {
        Session session = hibernateConfiguration.getMysqlSession();
        StudentGroups result = session.get(StudentGroups.class, id);
        session.close();
        return result;
    }

    public List<StudentGroups> findAll() {
        Session session = hibernateConfiguration.getMysqlSession();

        List<StudentGroups> studentGroupsList = (List<StudentGroups>) session.createQuery(
                "from StudentGroups where id>1").list();
        session.close();
        return studentGroupsList;
    }

}
