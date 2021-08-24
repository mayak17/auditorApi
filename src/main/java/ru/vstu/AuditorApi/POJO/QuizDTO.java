package ru.vstu.AuditorApi.POJO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class QuizDTO {
    private long grade;
    private long studentId;
    private long lessonId;
    private long lessonTypeId;
    private long attemptsCount;
    private Timestamp dateLastAttempt;

    public QuizDTO() {
        this.grade = 0;
        this.studentId = 0;
        this.lessonId = 0;
        this.lessonTypeId = 0;
    }
}
