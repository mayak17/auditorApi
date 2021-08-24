package ru.vstu.AuditorApi.POJO;


import lombok.Data;

import java.math.BigInteger;

@Data
public class LessonModDTO {
    private float modDifficultyRate;//entity-quizAttempts
    private long modNumber;//entity-chosenVariants
    private String description;//entity-Variants
    private BigInteger attemptsMod;//entity-ModAttempts
    private long allTestComplete;
}
