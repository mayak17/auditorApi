package ru.vstu.AuditorApi.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vstu.AuditorApi.POJO.ActualVariantDTO;
import ru.vstu.AuditorApi.configurations.HibernateConfiguration;
import ru.vstu.AuditorApi.entities.ChoosenVariants;

import java.util.List;

@Component
public class ChoosenVariantsDAO {

    @Autowired
    HibernateConfiguration hibernateConfiguration;


    public ChoosenVariants findById(int id) {
        Session session = hibernateConfiguration.getMysqlSession();
        ChoosenVariants result = session.get(ChoosenVariants.class, id);
        session.close();
        return result;
    }

    public void save(ChoosenVariants choosenVariants) {
        Session session = hibernateConfiguration.getMysqlSession();
        Transaction tx1 = session.beginTransaction();
        session.save(choosenVariants);
        tx1.commit();
        session.close();
    }

    public List<ChoosenVariants> findAll() {
        Session session = hibernateConfiguration.getMysqlSession();
        List<ChoosenVariants> choosenVariantsList = (List<ChoosenVariants>) session.createQuery("from ChoosenVariants").list();
        session.close();
        return choosenVariantsList;
    }

    public long findActualVariant(long studentId, long lessonTypeId) {
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session.createNativeQuery("SELECT variantid FROM v_actual_variants where studentid = :param1 and lessontypeid = :param2")
                .setParameter("param1", studentId)
                .setParameter("param2", lessonTypeId);
        List<Integer> actualVariantDTOList = query.list();

        session.close();
        if (actualVariantDTOList.size() > 0) {
            return (long) actualVariantDTOList.get(0);
        } else {
            return 0;
        }
    }
    public void deactivateVariant(long studentId , long variantId){
        Session session = hibernateConfiguration.getMysqlSession();
        Transaction tx1 = session.beginTransaction();
        session.createQuery("update ChoosenVariants set active = 0 where studentid=:param1 and variantid=:param2")
                .setParameter("param1",studentId)
                .setParameter("param2",variantId).executeUpdate();
        tx1.commit();
        session.close();
    }
}
