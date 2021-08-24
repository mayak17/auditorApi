package ru.vstu.AuditorApi.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="mod_attempts")
@NoArgsConstructor(force = true)
@Data
public class ModAttempts {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private long id;

  private long lessonid;
  private long studentid;
  private long modid;
  private long modcompid;
  private java.sql.Timestamp timegiven;
  private long active;

}
