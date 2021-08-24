package ru.vstu.AuditorApi.POJO;

import lombok.Data;

@Data
public class ActualVariantDTO {
    private int variantNum;
    private int lessonTypeId;
    private int studentId;
    private int lessonId;

}
