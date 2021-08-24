package ru.vstu.AuditorApi.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="algorithm_attempts")
@NoArgsConstructor(force = true)
@Data
public class AlgorithmAttempts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    private long lessontypeid;

    private long studentid;

    private long teacherid;

    private long grade;

    private long exported;

    private Timestamp timegraded;

    private String comments;
}
