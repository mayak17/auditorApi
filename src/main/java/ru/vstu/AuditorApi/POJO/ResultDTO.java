package ru.vstu.AuditorApi.POJO;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ResultDTO {
    private long gradeTest;
    private long attemptsTest;
    private long variant;
    private long attemptsSRS;
    private double modDifficulty;
    private BigInteger attemptsMod;
    private long minRequirementProtocol;
    private long minRequirementCodestyle;
    private long loadToMoodle;
    private long additionalRequirementProtocol;
    private long additionalRequirementCodestyle;
    private long gradeAlgorithm;
    private String commentsProtocol;
    private String commentsCodestyle;
    private String commentsAlgorithm;
}
