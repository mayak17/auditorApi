package ru.vstu.AuditorApi.POJO;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class ModInfoDTO {
    private long attemptsMod;
    private long actualVariantNum;
    private HashMap<Long,Float> allModDifficult;
    private long allTestPassed;
    private long currentModNum;
    private String currentModDescription;
    private long currentModCountTesting;
    private float currentModDifficult;
    private long currentModAttemptId;

    public ModInfoDTO() {
        this.attemptsMod = 0L;
        this.actualVariantNum = 0L;
        this.allModDifficult=null;
        this.allTestPassed = 0L;
        this.currentModNum=0L;
        this.currentModCountTesting=0L;
        this.currentModDescription="";
        this.currentModAttemptId=0L;
        this.currentModDifficult=0;
    }

}
