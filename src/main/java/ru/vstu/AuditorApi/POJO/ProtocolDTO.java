package ru.vstu.AuditorApi.POJO;

import lombok.Data;

@Data
public class ProtocolDTO {
   private long studentId;
   private long lessonTypeId;
   private long teacherId;
   private long minRequirementProtocol;
   private long minRequirementCodestyle;
   private long loadToMoodle;
   private long additionalRequirementProtocol;
   private long additionalRequirementCodestyle;
   private long gradeAlgorithm;
   private String commentsProtocol;
   private String commentsCodestyle;
   private String commentsAlgorithm;

   public ProtocolDTO() {
      this.studentId=0;
      this.lessonTypeId=0;
      this.teacherId=0;
      this.minRequirementProtocol = 0;
      this.minRequirementCodestyle = 0;
      this.loadToMoodle = 0;
      this.additionalRequirementProtocol = 0;
      this.additionalRequirementCodestyle = 0;
      this.gradeAlgorithm = 0;
      this.commentsProtocol = "";
      this.commentsCodestyle = "";
      this.commentsAlgorithm = "";
   }
}
