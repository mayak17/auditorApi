package ru.vstu.AuditorApi.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="codestyle_attempts")
@NoArgsConstructor(force = true)
@Data
public class CodestyleAttempts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    private Long teacherid;

    private Long minimum;

    private Long studentid;

    private Long isinmoodle;

    private Long grade;

    private Long exported;

    private String comments;

    private Long lessontypeid;

    private Timestamp timegraded;
}