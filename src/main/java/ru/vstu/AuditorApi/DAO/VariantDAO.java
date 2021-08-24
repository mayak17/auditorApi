package ru.vstu.AuditorApi.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vstu.AuditorApi.configurations.HibernateConfiguration;
import ru.vstu.AuditorApi.entities.Variant;

import java.util.List;

@Component
public class VariantDAO {

    @Autowired
    HibernateConfiguration hibernateConfiguration;

    public Variant findById(long id) {
        Session session = hibernateConfiguration.getMysqlSession();
        Variant result = session.get(Variant.class, id);
        session.close();
        return result;
    }

    public List<Variant> findAll() {
        Session session = hibernateConfiguration.getMysqlSession();
        List<Variant> variantList = (List<Variant>) session.createQuery("from Variant ").list();
        session.close();
        return variantList;
    }

    public long findVariantIdByNumVariantAndLessonTypeId(long numVariant, long lessonTypeId){
        Session session = hibernateConfiguration.getMysqlSession();

        List<Long> result = (List<Long>) session
                .createQuery("select id from Variant WHERE num = :param1 AND lessontypeid = :param2")
                .setParameter("param1", numVariant)
                .setParameter("param2", lessonTypeId).list();
        session.close();
        if(result.size()>0)
            return result.get(0);
        else
            return 0L;
    }
    public List<Long> findAllStudentIdWhichUseVariant(long variantId, long groupId){
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT studentid FROM v_actual_variants JOIN students ON students.id = v_actual_variants.studentid where variantid=:param1 and groupid =:param2")
                .setParameter("param1", variantId)
                .setParameter("param2", groupId)
                .addScalar("studentid", LongType.INSTANCE);
        List<Long> allStudentId = query.list();
        session.close();
        return allStudentId;
    }
}
