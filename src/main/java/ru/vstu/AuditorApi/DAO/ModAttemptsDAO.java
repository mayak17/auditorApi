package ru.vstu.AuditorApi.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.FloatType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vstu.AuditorApi.POJO.ActualVariantDTO;
import ru.vstu.AuditorApi.configurations.HibernateConfiguration;
import ru.vstu.AuditorApi.entities.ModAttempts;

import java.math.BigInteger;
import java.util.List;

@Component
public class ModAttemptsDAO {

    @Autowired
    HibernateConfiguration hibernateConfiguration;

    public ModAttempts findById(int id) {
        Session session = hibernateConfiguration.getMysqlSession();
        ModAttempts result = session.get(ModAttempts.class, id);
        session.close();
        return result;
    }

    public long save(ModAttempts modAttempts) {
        Session session = hibernateConfiguration.getMysqlSession();
        Transaction tx1 = session.beginTransaction();
        session.save(modAttempts);
        tx1.commit();
        session.close();
        return modAttempts.getId();
    }

    public List<ModAttempts> findAll() {
        Session session = hibernateConfiguration.getMysqlSession();
        List<ModAttempts> modAttemptsList = (List<ModAttempts>) session.createQuery("from ModAttempts ").list();
        session.close();
        return modAttemptsList;
    }
    public void finishLesson(long lessonId){
        Session session = hibernateConfiguration.getMysqlSession();
        Transaction tx1 = session.beginTransaction();
        session.createQuery("update ModAttempts set active = 0 where lessonid=:param1")
                .setParameter("param1",lessonId);
        session.close();
    }

    public long getAllAttemptsByStudentIdAndLessonTypeId(long studentId, long lessonTypeId){
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT attempt_count FROM vg_mod_count_attempts where studentid = :param1 and lessontypeid = :param2")
                .setParameter("param1", studentId)
                .setParameter("param2", lessonTypeId)
                .addScalar("attempt_count", LongType.INSTANCE);
        List<Long> attemptsCount = query.list();
        session.close();
        if(attemptsCount.size()>0)
            return attemptsCount.get(0);
        else
            return 0L;
    }

    public long doneModByStudentIdAndLessonTypeId(long studentId,long lessonTypeId){
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT done FROM vg_mod_testing where studentid = :param1 and lessontypeid = :param2")
                .setParameter("param1", studentId)
                .setParameter("param2", lessonTypeId)
                .addScalar("done",LongType.INSTANCE);
        List<Long> attemptsCount = query.list();
        session.close();
        if(attemptsCount.size()>0)
            return attemptsCount.get(0);
        else
            return 0L;
    }
    public long currentModAttemptId(long studentId,long lessonId,long lessonTypeId){
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT id FROM mod_attempts join v_mods on v_mods.modid = mod_attempts.modid where  lessonid=:param1 and studentid=:param2 and lessontypeid=:param3 and active=1")
                .setParameter("param1", lessonId)
                .setParameter("param2", studentId)
                .setParameter("param3",lessonTypeId)
                .addScalar("id",LongType.INSTANCE);
        List<Long> currentAttemptId = query.list();
        session.close();
        if(currentAttemptId.size()>0)
            return currentAttemptId.get(currentAttemptId.size()-1);
        else
            return 0;
    }
    public String currentModDescription(long studentId,long lessonId,long lessonTypeId){
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT moddesc FROM mod_attempts join v_mods on v_mods.modid = mod_attempts.modid where  lessonid=:param1 and studentid=:param2 and lessontypeid=:param3 and active=1")
                .setParameter("param1", lessonId)
                .setParameter("param2", studentId)
                .setParameter("param3",lessonTypeId)
                .addScalar("moddesc", StringType.INSTANCE);
        List<String> currentDescription = query.list();
        session.close();
        if(currentDescription.size()>0)
            return currentDescription.get(currentDescription.size()-1);
        else
            return "";
    }
    public long currentModNum(long studentId,long lessonId,long lessonTypeId){
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT modnum FROM mod_attempts join v_mods on v_mods.modid = mod_attempts.modid where  lessonid=:param1 and studentid=:param2 and lessontypeid=:param3 and active=1")
                .setParameter("param1", lessonId)
                .setParameter("param2", studentId)
                .setParameter("param3",lessonTypeId)
                .addScalar("modnum", LongType.INSTANCE);
        List<Long> currentModNum = query.list();
        session.close();
        if(currentModNum.size()>0)
            return currentModNum.get(currentModNum.size()-1);
        else
            return 0L;
    }
    public float currentModDifficult(long studentId,long lessonId,long lessonTypeId){
        Session session = hibernateConfiguration.getMysqlSession();

        NativeQuery query = session
                .createNativeQuery("SELECT kc FROM mod_attempts join v_mods on v_mods.modid = mod_attempts.modid where  lessonid=:param1 and studentid=:param2 and lessontypeid=:param3 and active=1")
                .setParameter("param1", lessonId)
                .setParameter("param2", studentId)
                .setParameter("param3",lessonTypeId)
                .addScalar("kc", FloatType.INSTANCE);
        List<Float> currentModNum = query.list();
        session.close();
        if(currentModNum.size()>0)
            return currentModNum.get(currentModNum.size()-1);
        else
            return 0;
    }
}
