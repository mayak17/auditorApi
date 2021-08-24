package ru.vstu.AuditorApi.POJO;

import lombok.Data;

@Data
public class LessonHomeworkDTO {
    private long variantNum;//entity-chosenVariants
    private String description;//entity-Variants
    private String filenameSrs;
    private long formallyCorrect;
    private long lessonid;
    private long attemptsSRS;//entity-SrsAttempts
    private long srsComplete;

}
