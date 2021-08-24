package ru.vstu.AuditorApi.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="quiz_attempts")
@Data
public class QuizAttempts {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private long id;
  private long lessonid;
  private long lessontypeid;
  private long studentid;
  private long grade;
  private java.sql.Timestamp timeattempt;
  private String comments;

  public QuizAttempts() {
    this.lessonid = 0;
    this.lessontypeid = 0;
    this.studentid = 0;
    this.grade = 0;
    this.timeattempt = new Timestamp(System.currentTimeMillis());
    this.comments = " ";
  }

}
