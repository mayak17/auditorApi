package ru.vstu.AuditorApi.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="protocol_attempts")
@NoArgsConstructor(force = true)
@Data
public class ProtocolAttempts {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private int id;
  private long lessontypeid;
  private long studentid;
  private long teacherid;
  private long minimum;
  private long grade;
  private long exported;
  private String comments;
  private java.sql.Timestamp timegraded;

}
