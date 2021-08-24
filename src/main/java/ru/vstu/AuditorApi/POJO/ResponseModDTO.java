package ru.vstu.AuditorApi.POJO;

import lombok.Data;

@Data
public class ResponseModDTO {
    private long modAttemptId;
    private String description;

    public ResponseModDTO() {
        this.modAttemptId = 0;
        this.description = "";
    }

}
