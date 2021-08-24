package ru.vstu.AuditorApi.POJO;

import lombok.Data;

@Data
public class ModStartDTO {
    private long studentId;
    private long lessonId;
    private float difficult;
    private long variantNum;
    private long modNum;
    private long lessonTypeId;
}
