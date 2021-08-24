package ru.vstu.AuditorApi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vstu.AuditorApi.DAO.AlgorithmAttemptsDAO;
import ru.vstu.AuditorApi.DAO.CodestyleAttemptsDAO;
import ru.vstu.AuditorApi.DAO.ProtocolAttemptsDAO;
import ru.vstu.AuditorApi.POJO.ProtocolDTO;
import ru.vstu.AuditorApi.entities.AlgorithmAttempts;
import ru.vstu.AuditorApi.entities.CodestyleAttempts;
import ru.vstu.AuditorApi.entities.ProtocolAttempts;

import java.sql.Timestamp;

@Service
public class ProtocolService {
    @Autowired
    ProtocolAttemptsDAO protocolAttemptsDAO;
    @Autowired
    CodestyleAttemptsDAO codestyleAttemptsDAO;
    @Autowired
    AlgorithmAttemptsDAO algorithmAttemptsDAO;


    public ProtocolDTO generateProtocolDTO(long studentId, long lessonTypeId){

        ProtocolDTO dto = new ProtocolDTO();

        ProtocolAttempts protocol = protocolAttemptsDAO
                .findLatestByStudentIdAndLessonTypeId(studentId,lessonTypeId);
        CodestyleAttempts codestyle = codestyleAttemptsDAO.findLatestByStudentIdAndLessonTypeId(studentId,lessonTypeId);
        AlgorithmAttempts algorithm = algorithmAttemptsDAO.findLatestByStudentIdAndLessonTypeId(studentId,lessonTypeId);
        if(protocol!=null) {
            dto.setAdditionalRequirementProtocol((int) protocol.getGrade());
            dto.setCommentsProtocol(protocol.getComments());
            dto.setMinRequirementProtocol((int) protocol.getMinimum());
        }
        if(codestyle!=null) {
            dto.setAdditionalRequirementCodestyle(codestyle.getGrade());
            dto.setCommentsCodestyle(codestyle.getComments());
            dto.setMinRequirementCodestyle(codestyle.getMinimum());
            dto.setLoadToMoodle(codestyle.getIsinmoodle());
        }
        if(algorithm!=null) {
            dto.setCommentsAlgorithm(algorithm.getComments());
            dto.setGradeAlgorithm(algorithm.getGrade());
        }

        return dto;
    }

    public void addAttemptsProtocolCodestyleAlgorithm(ProtocolDTO protocolRecord){
        protocolAttemptsDAO.save(fillProtocolAttempts(protocolRecord));
        algorithmAttemptsDAO.save(fillAlgorithmAttempts(protocolRecord));
        codestyleAttemptsDAO.save(fillCodestyleAttempts(protocolRecord));
    }
    public ProtocolAttempts fillProtocolAttempts(ProtocolDTO protocolRecord){
        ProtocolAttempts protocolAttempts = new ProtocolAttempts();

        protocolAttempts.setComments(protocolRecord.getCommentsProtocol());
        protocolAttempts.setStudentid(protocolRecord.getStudentId());
        protocolAttempts.setLessontypeid(protocolRecord.getLessonTypeId());
        protocolAttempts.setGrade(protocolRecord.getAdditionalRequirementProtocol());
        protocolAttempts.setExported(0);
        protocolAttempts.setMinimum(protocolRecord.getMinRequirementProtocol());
        protocolAttempts.setTeacherid(protocolRecord.getTeacherId());
        protocolAttempts.setTimegraded(new Timestamp(System.currentTimeMillis()));
        return protocolAttempts;
    }

    public AlgorithmAttempts fillAlgorithmAttempts(ProtocolDTO protocolRecord) {
        AlgorithmAttempts algorithmAttempts = new AlgorithmAttempts();
        algorithmAttempts.setExported(0L);
        algorithmAttempts.setTimegraded(new Timestamp(System.currentTimeMillis()));
        algorithmAttempts.setComments(protocolRecord.getCommentsAlgorithm());
        algorithmAttempts.setGrade(protocolRecord.getGradeAlgorithm());
        algorithmAttempts.setLessontypeid(protocolRecord.getLessonTypeId());
        algorithmAttempts.setTeacherid(protocolRecord.getTeacherId());
        algorithmAttempts.setStudentid(protocolRecord.getStudentId());

        return algorithmAttempts;
    }
    public CodestyleAttempts fillCodestyleAttempts(ProtocolDTO protocolRecord){
        CodestyleAttempts codestyleAttempts = new CodestyleAttempts();

        codestyleAttempts.setComments(protocolRecord.getCommentsCodestyle());
        codestyleAttempts.setExported(0L);
        codestyleAttempts.setStudentid(protocolRecord.getStudentId());
        codestyleAttempts.setGrade(protocolRecord.getAdditionalRequirementCodestyle());
        codestyleAttempts.setLessontypeid(protocolRecord.getLessonTypeId());
        codestyleAttempts.setTeacherid(protocolRecord.getTeacherId());
        codestyleAttempts.setIsinmoodle(protocolRecord.getLoadToMoodle());
        codestyleAttempts.setMinimum(protocolRecord.getMinRequirementCodestyle());
        codestyleAttempts.setTimegraded(new Timestamp(System.currentTimeMillis()));
        return codestyleAttempts;
    }
}
