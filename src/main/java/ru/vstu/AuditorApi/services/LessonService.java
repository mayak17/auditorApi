package ru.vstu.AuditorApi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vstu.AuditorApi.DAO.*;
import ru.vstu.AuditorApi.POJO.*;
import ru.vstu.AuditorApi.entities.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonService {
    @Autowired
    private LessonTypesDAO lessonTypesDAO ;
    @Autowired
    private ProtocolService protocolService;
    @Autowired
    private ModificationsDAO modificationsDAO;
    @Autowired
    private VariantDAO variantDAO;
    @Autowired
    private ChoosenVariantsDAO choosenVariantsDAO;
    @Autowired
    private ModAttemptsDAO modAttemptsDAO;
    @Autowired
    private QuizAttemptsDAO quizAttemptsDAO;
    @Autowired
    private SrsAttemptsDAO srsAttemptsDAO;
    @Autowired
    private PlacesDAO placesDAO;
    @Autowired
    private LessonsDAO lessonsDAO;
    @Autowired
    private ModTestingDAO modTestingDAO;
    @Autowired
    private StudentDAO studentDAO;

    public List<LessonListDTO> createLessonList(){
        return lessonTypesDAO.findAll().stream().map(LessonService::mapToLessonListDto).collect(Collectors.toList());
    }

    public List<Places> createPlacesList() {
        return this.placesDAO.findAll();
    }


    private static LessonListDTO mapToLessonListDto(LessonTypes lessonTypes) {
        LessonListDTO dto = new LessonListDTO();
        dto.setId(lessonTypes.getId());
        dto.setNameLesson(lessonTypes.getName());
        dto.setNum(lessonTypes.getNum());
        return dto;
    }

    public QuizDTO generateQuizDTO(long studentId, long lessonTypeId){
        QuizAttempts quizAttempts = quizAttemptsDAO.findMaxGradeByStudentIdAndLessonTypeId(studentId,lessonTypeId);
        QuizDTO quizDTO = new QuizDTO();
        if(quizAttempts!=null) {
            quizDTO.setLessonId(quizAttempts.getLessonid());
            quizDTO.setLessonTypeId(quizAttempts.getLessontypeid());
            quizDTO.setGrade(quizAttempts.getGrade());
            quizDTO.setStudentId(quizAttempts.getStudentid());
            quizDTO.setDateLastAttempt(quizAttempts.getTimeattempt());
            quizDTO.setAttemptsCount(quizAttemptsDAO.findValueAttempts(studentId,lessonTypeId));
        }
        return quizDTO;
    }

    public LessonHomeworkDTO generateLessonHomeworkDTO(long studentId, long lessonTypeId){
        LessonHomeworkDTO dto = new LessonHomeworkDTO();

        long variantId=choosenVariantsDAO.findActualVariant(studentId,lessonTypeId);
        SrsAttempts lastSrsAttempts= srsAttemptsDAO.findLatestByStudentIdAndVariantId(studentId, variantId);
        Variant variant;
        if (variantId > 0) {
            variant = variantDAO.findById(variantId);
            dto.setVariantNum(variant.getNum());
            dto.setDescription(variant.getDescription());
        }
        if(lastSrsAttempts!=null) {
            dto.setAttemptsSRS(srsAttemptsDAO.findValueAttempts(studentId, variantId));
            dto.setFilenameSrs(lastSrsAttempts.getFilename());
            dto.setSrsComplete(lastSrsAttempts.getAlltestspassed());
        }
        return dto;
    }

    public LessonModDTO generateLessonModDTO(long studentId,long lessonTypeId){
        LessonModDTO dto = new LessonModDTO();
        long variantId=choosenVariantsDAO.findActualVariant(studentId,lessonTypeId);
        dto.setAttemptsMod(modificationsDAO.findModAttempt(studentId,lessonTypeId));
        dto.setModDifficultyRate(modificationsDAO.findModDifficultRate(studentId,lessonTypeId));
        dto.setModNumber(modificationsDAO.findModNumber(studentId,lessonTypeId));
        dto.setDescription(modificationsDAO.findDescriptionMod(variantId, dto.getModNumber()));
        dto.setAllTestComplete(modificationsDAO.findModComplete(studentId,lessonTypeId)) ;
        return dto;
    }
    public ResultDTO generateResultDTO(int studentId,int lessonTypeId){
        ResultDTO resultDTO= new ResultDTO();
        LessonHomeworkDTO lessonHTDTO = generateLessonHomeworkDTO(studentId,lessonTypeId);
        LessonModDTO lessonModDTO = generateLessonModDTO(studentId,lessonTypeId);
        ProtocolDTO protocolDTO = protocolService.generateProtocolDTO(studentId,lessonTypeId);

        resultDTO.setVariant(lessonHTDTO.getVariantNum());
        resultDTO.setAttemptsSRS(lessonHTDTO.getAttemptsSRS());
        resultDTO.setModDifficulty(lessonModDTO.getModDifficultyRate());
        resultDTO.setAttemptsMod(lessonModDTO.getAttemptsMod());
        resultDTO.setMinRequirementProtocol(protocolDTO.getMinRequirementProtocol());
        resultDTO.setMinRequirementCodestyle(protocolDTO.getMinRequirementCodestyle());
        resultDTO.setLoadToMoodle(protocolDTO.getLoadToMoodle());
        resultDTO.setAdditionalRequirementProtocol(protocolDTO.getAdditionalRequirementProtocol());
        resultDTO.setAdditionalRequirementCodestyle(protocolDTO.getAdditionalRequirementCodestyle());
        resultDTO.setGradeAlgorithm(protocolDTO.getGradeAlgorithm());
        resultDTO.setCommentsProtocol(protocolDTO.getCommentsProtocol());
        resultDTO.setCommentsCodestyle(protocolDTO.getCommentsCodestyle());
        resultDTO.setCommentsAlgorithm(protocolDTO.getCommentsAlgorithm());

        return resultDTO;
    }

    public void addAttemptsQuiz(QuizDTO quizDTO){
        QuizAttempts quizAttempts = fillQuizAttempts(quizDTO);
        if(quizAttempts.getLessonid()>0)
            quizAttemptsDAO.save(quizAttempts);
    }
    public long isVariantExist(long lessonTypeId, long numberVariant){
        return(variantDAO.findVariantIdByNumVariantAndLessonTypeId(numberVariant,lessonTypeId));
    }
    public List<Long> isVariantAlreadyUse(long varId,long groupId){
        return this.variantDAO.findAllStudentIdWhichUseVariant(varId,groupId);
    }
    public void saveChosenVariant(ActualVariantDTO actualVariantDTO){
        long actualVariant = choosenVariantsDAO.findActualVariant(actualVariantDTO.getStudentId(),actualVariantDTO.getLessonTypeId());
        if(actualVariant != 0) {
            choosenVariantsDAO.deactivateVariant(actualVariantDTO.getStudentId(), actualVariant);
        }
        choosenVariantsDAO.save( fillChoosenVariants(actualVariantDTO) );
    }
    public ChoosenVariants fillChoosenVariants(ActualVariantDTO actualVariantDTO){
        ChoosenVariants choosenVariantsTemp = new ChoosenVariants();
        long variantId = variantDAO.findVariantIdByNumVariantAndLessonTypeId(actualVariantDTO.getVariantNum(),actualVariantDTO.getLessonTypeId());

        choosenVariantsTemp.setLessonid(actualVariantDTO.getLessonId());
        choosenVariantsTemp.setVariantid(variantId);
        choosenVariantsTemp.setStudentid(actualVariantDTO.getStudentId());
        choosenVariantsTemp.setActive(1);
        return choosenVariantsTemp;
    }
    public ResponseModDTO startModification(ModStartDTO modStartDTO){
        ResponseModDTO responseModDTO = new ResponseModDTO();
        ModAttempts modAttempts = this.fillModAttempts(modStartDTO);
        long modAttemptId = modAttemptsDAO.save(modAttempts);
        responseModDTO.setModAttemptId(modAttemptId);
        responseModDTO.setDescription(modificationsDAO.findById(modAttempts.getModid()).getDescription());
        return responseModDTO;
    }
    public ModAttempts fillModAttempts(ModStartDTO modStartDTO){
        ModAttempts modAttempts = new ModAttempts();
        modAttempts.setLessonid(modStartDTO.getLessonId());
        modAttempts.setStudentid(modStartDTO.getStudentId());
        modAttempts.setModid(modificationsDAO
                .findModIdByVariantNumAndModNumAndLessonTypeId(modStartDTO.getVariantNum()
                        , modStartDTO.getModNum()
                        , modStartDTO.getLessonTypeId()));
        modAttempts.setModcompid(2);
        modAttempts.setTimegiven(new Timestamp(System.currentTimeMillis()));
        modAttempts.setActive(1);
        return modAttempts;
    }
    public QuizAttempts fillQuizAttempts(QuizDTO quizDTO){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        QuizAttempts quizAttemptsTemp = new QuizAttempts();

        quizAttemptsTemp.setComments("");
        quizAttemptsTemp.setLessonid(quizDTO.getLessonId());
        quizAttemptsTemp.setGrade(quizDTO.getGrade());
        quizAttemptsTemp.setStudentid(quizDTO.getStudentId());
        quizAttemptsTemp.setLessontypeid(quizDTO.getLessonTypeId());
        quizAttemptsTemp.setTimeattempt(timestamp);
        return quizAttemptsTemp;
    }
    public long startLesson(Lessons lessons){
        java.sql.Date myAnotherDate = new java.sql.Date( (new java.util.Date()).getTime());
        lessons.setDateoflesson(myAnotherDate);
        lessons.setNameoflesson("");
        long id=lessonsDAO.isLessonStarted(lessons);
        if (id==0)
            return lessonsDAO.save(lessons);
        else
            return id;
    }
    public void finishLesson(long lessonId){
        modAttemptsDAO.finishLesson(lessonId);
    }

    public void addSrsAttempts(LessonHomeworkDTO lessonHomeworkDTO,long studentId, long lessonId, long lessonTypeId){
        srsAttemptsDAO.save(fillSrsAttempts(lessonHomeworkDTO,studentId,lessonId,lessonTypeId));
    }
    public SrsAttempts fillSrsAttempts(LessonHomeworkDTO srsWork,long studentId, long lessonId, long lessonTypeId){
        SrsAttempts srsAttemptsTemp = new SrsAttempts();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        srsAttemptsTemp.setLessonid(lessonId);
        srsAttemptsTemp.setComments("");
        srsAttemptsTemp.setAlltestspassed(srsWork.getSrsComplete());
        srsAttemptsTemp.setFilename(srsWork.getFilenameSrs());
        srsAttemptsTemp.setStudentid(studentId);
        srsAttemptsTemp.setTimetesting(timestamp);
        srsAttemptsTemp.setVariantid(variantDAO.findVariantIdByNumVariantAndLessonTypeId(srsWork.getVariantNum(),lessonTypeId));
        srsAttemptsTemp.setFormallycorrect(srsWork.getFormallyCorrect());
        return srsAttemptsTemp;
    }

    public void addModTesting(ModTesting modTesting){
        modTesting.setComments("");
        modTesting.setTimetesting(new Timestamp(System.currentTimeMillis()));
        modTestingDAO.save(modTesting);
    }

    public ModInfoDTO generateModInfoDTO(long studentId,long lessonTypeId,long lessonId){
        ModInfoDTO modInfoDTO = new ModInfoDTO();
        long variantId=choosenVariantsDAO.findActualVariant(studentId,lessonTypeId);

        modInfoDTO.setAttemptsMod(modAttemptsDAO.getAllAttemptsByStudentIdAndLessonTypeId(studentId,lessonTypeId));
        modInfoDTO.setActualVariantNum(variantDAO.findById(variantId).getNum());
        modInfoDTO.setAllModDifficult(this.mapModNumAndDifficult(studentId,variantId));
        modInfoDTO.setAllTestPassed(modAttemptsDAO.doneModByStudentIdAndLessonTypeId(studentId,lessonTypeId));
        modInfoDTO.setCurrentModDescription(modAttemptsDAO.currentModDescription(studentId,lessonId,lessonTypeId));
        modInfoDTO.setCurrentModAttemptId(modAttemptsDAO.currentModAttemptId(studentId,lessonId,lessonTypeId));
        modInfoDTO.setCurrentModNum(modAttemptsDAO.currentModNum(studentId,lessonId,lessonTypeId));
        modInfoDTO.setCurrentModDifficult(modAttemptsDAO.currentModDifficult(studentId,lessonId,lessonTypeId));
        modInfoDTO.setCurrentModCountTesting(modTestingDAO.countCurrentTesting(modInfoDTO.getCurrentModAttemptId()));
        return modInfoDTO;
    }
    public boolean isSrsCompleted(long studentId,long lessonTypeId){
        long variantId=choosenVariantsDAO.findActualVariant(studentId,lessonTypeId);
        return this.srsAttemptsDAO.isSrsComleted(studentId,variantId);
    }
    private HashMap<Long,Float> mapModNumAndDifficult(long studentId,long variantId){
        List<Long> allModNum = modificationsDAO.findAllModNumByVariantId(variantId);
        List<Float>allDifficult=modificationsDAO.findAllDifficultByVariantId(variantId);
        List<Long> allUsedNum=modificationsDAO.findAllUsedModificationByStudentIdAndVariantId(studentId,variantId);
        HashMap<Long,Float> map = new HashMap<>();
        for(int i=0;i<allModNum.size();i++){
            if(!allUsedNum.contains(allModNum.get(i)))
                map.put(allModNum.get(i),allDifficult.get(i));
        }
        return map;
    }
    public ResultPointDTO generateResultPointDTO(long studentId,long lessonTypeId){
        return ResultPointDTO
                .mapResultPointDTO(studentDAO.getAllPointByStudentIdAndLessonTypeId(studentId,lessonTypeId));

    }

    public float getResultGrade(long studentId){
        return this.studentDAO.sumAllGradeStudent(studentId);
    }
}
