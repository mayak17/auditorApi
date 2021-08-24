package ru.vstu.AuditorApi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vstu.AuditorApi.DAO.ProtocolAttemptsDAO;
import ru.vstu.AuditorApi.POJO.*;
import ru.vstu.AuditorApi.entities.*;
import ru.vstu.AuditorApi.services.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LessonController {
    @Autowired
    private LessonService lessonService;
    @Autowired
    private SemesterService semesterService;
    @Autowired
    private ProtocolService protocolService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ProtocolAttemptsDAO protocolAttemptsDAO;

    public LessonController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/group/student/homework/{studentId}/{lessonId}/{lessonTypeId}")
    public ResponseEntity addAttemptsSrsAndTestOnLesson(@RequestBody LessonHomeworkDTO srsWork,
                                                        @PathVariable long studentId,
                                                        @PathVariable long lessonId,
                                                        @PathVariable long lessonTypeId) {
            this.lessonService.addSrsAttempts(srsWork,studentId,lessonId, lessonTypeId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/group/student/checkVariant")
    public ResponseEntity<List<Long>> checkChosenVariant(@RequestParam long lessonTypeId,
                                                   @RequestParam long numberVariant,
                                                   @RequestParam long groupId){
        long varId=this.lessonService.isVariantExist(lessonTypeId,numberVariant);
        if(varId!=0L && this.semesterService.getActiveSemesterCode().equals("hll1")) {
            if(lessonTypeId!=1 && lessonTypeId!=6) {
                List<Long> allStudentId = this.lessonService.isVariantAlreadyUse(varId, groupId);
                if (allStudentId.size() > 0)
                    return new ResponseEntity<>(allStudentId, HttpStatus.CONFLICT);
                else
                    return ResponseEntity.ok().build();
            }
            else return ResponseEntity.ok().build();
        }
        else if(varId!=0L && this.semesterService.getActiveSemesterCode().equals("hll2")){
            List<Long> allStudentId = this.lessonService.isVariantAlreadyUse(varId, groupId);
            if (allStudentId.size() > 0)
                return new ResponseEntity<>(allStudentId, HttpStatus.CONFLICT);
            else
                return ResponseEntity.ok().build();
        }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PostMapping("/group/student/saveVariant")
    public ResponseEntity saveChosenVariant(@RequestBody ActualVariantDTO actualVariantDTO){
        this.lessonService.saveChosenVariant(actualVariantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/group/student/givemod")
    public ResponseEntity<ResponseModDTO> giveModification(@RequestBody ModStartDTO modStartDTO){
        ResponseModDTO responseModDTO = this.lessonService.startModification(modStartDTO);
        return new ResponseEntity<>(responseModDTO, HttpStatus.CREATED);
    }
    @PostMapping("/group/student/addQuiz")
    public ResponseEntity saveChosenVariant(@RequestBody QuizDTO quizDTO){
        this.lessonService.addAttemptsQuiz(quizDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/start")
    public ResponseEntity<Long> startLesson(@RequestBody Lessons lessons){
        long id = this.lessonService.startLesson(lessons);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
    @PutMapping("/finishlesson/{lessonId}")
    public ResponseEntity finishLesson(@PathVariable long lessonId){
        this.lessonService.finishLesson(lessonId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/groups")
    public ResponseEntity<List<StudentGroups>> getStudentGroupsList() {
        List<StudentGroups> studentGroups = this.groupService.findAllGroups();
            return ResponseEntity.ok(studentGroups);
    }
    @GetMapping("/lessons")
    public ResponseEntity<List<LessonListDTO>> getAllLessonTypes() {
        try {
            List<LessonListDTO> allLessonTypes = this.lessonService.createLessonList();
            return ResponseEntity.ok(allLessonTypes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/group/students")
    public ResponseEntity<List<Student>> getAllStudentsInGroup(@RequestParam int groupId) {
            List<Student> allStudentsInGroup = this.studentService.findByGroupId(groupId);
            return ResponseEntity.ok(allStudentsInGroup);
    }
    @GetMapping("/group/student/modwork")
    public ResponseEntity<ModInfoDTO> getModInfo(@RequestParam long studentId,
                                                 @RequestParam long lessonTypeId,
                                                 @RequestParam long lessonId){
        if(this.lessonService.isSrsCompleted(studentId,lessonTypeId)) {
            ModInfoDTO modInfoDTO = this.lessonService.generateModInfoDTO(studentId, lessonTypeId,lessonId);
            return ResponseEntity.ok(modInfoDTO);
        }
        else
            return ResponseEntity.notFound().build();
    }
    @GetMapping("/group/student/resultPoint")
    public ResponseEntity<ResultPointDTO> getResultPoint(@RequestParam long studentId,
                                                         @RequestParam long lessonTypeId){
        return ResponseEntity.ok(this.lessonService.generateResultPointDTO(studentId,lessonTypeId));
    }
    @GetMapping("/group/student/quiz")
    public ResponseEntity<QuizDTO> getBestQuiz(@RequestParam long studentId,
                                               @RequestParam long lessonTypeId){
            QuizDTO quizDTO = this.lessonService.generateQuizDTO(studentId,lessonTypeId);
            if(quizDTO!=null)
                return ResponseEntity.ok(quizDTO);
            else
                return ResponseEntity.ok(new QuizDTO());
    }
    @GetMapping("/group/student/homework")
    public ResponseEntity<LessonHomeworkDTO> getStudentsHomework(@RequestParam long studentId,
                                                                        @RequestParam long lessonTypeId) {
        LessonHomeworkDTO homeworkResult = this.lessonService.generateLessonHomeworkDTO(studentId,lessonTypeId);
        return ResponseEntity.ok(homeworkResult);
    }

    @GetMapping("/places")
    public ResponseEntity<List<Places>> getAllPlaces() {
        try {
            List<Places> allPlaces = this.lessonService.createPlacesList();
            return ResponseEntity.ok(allPlaces);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/group/student/protocol")
    public ResponseEntity<ProtocolDTO> getStudentsProtocol(@RequestParam int studentId,
                                                                    @RequestParam int lessonTypeId) {
        ProtocolDTO protocol = this.protocolService.generateProtocolDTO(studentId,lessonTypeId);
        return ResponseEntity.ok(protocol);
    }
    @PostMapping("/group/student/protocol")
    public ResponseEntity addProtocol(@RequestBody ProtocolDTO protocolDTO){
        this.protocolService.addAttemptsProtocolCodestyleAlgorithm(protocolDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/group/student/result")
    public ResponseEntity<ResultDTO> getStudentsResult(@RequestParam int studentId,
                                                       @RequestParam int lessonTypeId) {
        ResultDTO result = this.lessonService.generateResultDTO(studentId,lessonTypeId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/labDetails")
    public ResponseEntity<List> getDetails(@RequestParam long studentId,
                                                   @RequestParam long lessonTypeId){
        return ResponseEntity.ok(protocolAttemptsDAO.testResult(studentId,lessonTypeId));
    }
    @PostMapping("/group/student/testModification")
    public ResponseEntity addTestModification(@RequestBody ModTesting modTesting){
        this.lessonService.addModTesting(modTesting);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/group/student/resultGrade")
    public ResponseEntity<Float> getResultGradeStudent(@RequestParam long studentId){
        float grade = this.lessonService.getResultGrade(studentId);
        return ResponseEntity.ok(grade);
    }

}
