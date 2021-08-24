package ru.vstu.AuditorApi.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="mod_testing")
@NoArgsConstructor(force = true)
@Data
public class ModTesting {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private long id;

  private long modattemptid;
  private java.sql.Timestamp timetesting;
  private String filename;
  private long formallycorrect;
  private long alltestspassed;
  private String comments;

}
