package ru.vstu.AuditorApi.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="srs_attempts")
@NoArgsConstructor(force = true)
@Data
public class SrsAttempts {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private long id;
  private long lessonid;
  private long studentid;
  private long variantid;
  private java.sql.Timestamp timetesting;
  private String filename;
  private long formallycorrect;
  private long alltestspassed;
  private String comments;

}
