package ru.vstu.AuditorApi.DAO;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.FloatType;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vstu.AuditorApi.POJO.ResultPointDTO;
import ru.vstu.AuditorApi.configurations.HibernateConfiguration;
import ru.vstu.AuditorApi.entities.Student;

import java.util.List;

@Component
public class StudentDAO {
    @Autowired
    HibernateConfiguration hibernateConfiguration;

    public Student findById(int id) {
        Session session = hibernateConfiguration.getMysqlSession();

        Student result = session.get(Student.class, id);
        session.close();
        return result;
    }

    public List<Student> findAllByGroupId(int groupId) {
        Session session = hibernateConfiguration.getMysqlSession();
        List<Student> studentList = (List<Student>) session
                .createQuery("from Student where groupid=:paramName")
                .setParameter("paramName", groupId)
                .list();
        session.close();
        return studentList;
    }
    public float sumAllGradeStudent(long studentId){
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT sum(quiz_result+srs_result+mod_result+codestyle_result+algorithm_result+protocol_result) FROM vg_lab_grades_calc where studentid =:param1")
                .setParameter("param1", studentId)
                .addScalar("sum(quiz_result+srs_result+mod_result+codestyle_result+algorithm_result+protocol_result)", FloatType.INSTANCE);
        float sumGrade = (float)query.list().get(0);
        session.close();
        return sumGrade;
    }
    public Object[] getAllPointByStudentIdAndLessonTypeId(long studentId,long lessonTypeId){
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query =  session
                .createNativeQuery("SELECT quiz_result,srs_result,mod_result,codestyle_result,algorithm_result,protocol_result FROM vg_lab_grades_calc where studentid=:param1 and lessonTypeId=:param2")
                .setParameter("param1", studentId)
                .setParameter("param2",lessonTypeId)
                .addScalar("quiz_result",FloatType.INSTANCE)
                .addScalar("srs_result",FloatType.INSTANCE)
                .addScalar("mod_result",FloatType.INSTANCE)
                .addScalar("codestyle_result",FloatType.INSTANCE)
                .addScalar("algorithm_result",FloatType.INSTANCE)
                .addScalar("protocol_result",FloatType.INSTANCE);

        Object[] allPoint =  (Object[])query.list().get(0);
        session.close();
        return allPoint;
    }
}
