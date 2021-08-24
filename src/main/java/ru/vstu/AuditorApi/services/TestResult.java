package ru.vstu.AuditorApi.services;

import lombok.Data;

@Data
public class TestResult {

    protected String programName;
    protected Integer labNum;
    protected Integer variant;
    protected Integer mod;
    protected String expected;
    protected String real;

}
