package ru.vstu.AuditorApi.DAO;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vstu.AuditorApi.configurations.HibernateConfiguration;
import ru.vstu.AuditorApi.entities.Modifications;

import java.math.BigInteger;
import java.util.List;

@Component
public class ModificationsDAO {

    @Autowired
    HibernateConfiguration hibernateConfiguration;

    public Modifications findById(Long id) {
        Session session = hibernateConfiguration.getMysqlSession();
        Modifications result = session.get(Modifications.class, id);
        session.close();
        return result;
    }

    public float findModDifficultRate(long studentId, long lessonTypeId) {
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT kc FROM vg_mod_testing where studentid = :param1 and lessontypeid = :param2")
                .setParameter("param1", studentId)
                .setParameter("param2", lessonTypeId);
        List<Float> rate = query.list();
        session.close();
        return rate.get(0);
    }

    public BigInteger findModAttempt(long studentId, long lessonTypeId) {
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT testing_count FROM vg_mod_testing where studentid = :param1 and lessontypeid = :param2")
                .setParameter("param1", studentId)
                .setParameter("param2", lessonTypeId);
        List<BigInteger> testingCount = query.list();
        session.close();
        return testingCount.get(0);
    }

    public long findModNumber(long studentId, long lessonTypeId) {
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT modnum FROM vg_mod_testing where studentid = :param1 and lessontypeid = :param2")
                .setParameter("param1", studentId)
                .setParameter("param2", lessonTypeId);
        List<Integer> modNumber = query.list();
        session.close();
        return (long) modNumber.get(0);
    }

    public String findDescriptionMod(long variantId, long modNumber) {
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT moddesc FROM v_mods where variantid = :param1 and modnum = :param2")
                .setParameter("param1", variantId)
                .setParameter("param2", modNumber);
        List<String> description = query.list();
        session.close();
        return description.get(0);
    }

    public long findModComplete(long studentId, long lessonTypeId) {
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT done FROM vg_mod_testing where studentid = :param1 and lessontypeid = :param2")
                .setParameter("param1", studentId)
                .setParameter("param2", lessonTypeId);
        List<Integer> modComplete = query.list();
        session.close();
        return (long) modComplete.get(0);
    }

    public List<Float> findAllDifficultByVariantId(long variantId){
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT kc FROM v_mods where variantid = :param1")
                .setParameter("param1", variantId);
        List<Float> modDifficultList = query.list();
        session.close();
        return modDifficultList;
    }
    public List<Long> findAllModNumByVariantId(long variantId){
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT modnum FROM v_mods where variantid = :param1")
                .setParameter("param1", variantId)
                .addScalar("modnum",LongType.INSTANCE);
        List<Long> modNumList = query.list();
        session.close();
        return modNumList;
    }
    public List<Long> findAllUsedModificationByStudentIdAndVariantId(long studentId, long variantId){
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT modnum FROM mod_attempts join v_mods on mod_attempts.modid = v_mods.modid where studentid=:param1 and variantid=:param2 order by modnum")
                .setParameter("param1", studentId)
                .setParameter("param2",variantId)
                .addScalar("modnum",LongType.INSTANCE);
        List<Long> modNumList = query.list();
        session.close();
        return modNumList;
    }
    public long findModIdByVariantNumAndModNumAndLessonTypeId(long variantNum,long modNum,long lessonTypeId){
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT modid FROM v_mods where varnum = :param1 and modnum=:param2 and lessontypeid=:param3")
                .setParameter("param1", variantNum)
                .setParameter("param2", modNum)
                .setParameter("param3",lessonTypeId)
                .addScalar("modid", LongType.INSTANCE);
        long modId = (long) query.list().get(0);
        session.close();
        return modId;
    }
}
