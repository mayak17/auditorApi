package ru.vstu.AuditorApi.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="student_groups")
@NoArgsConstructor(force = true)
@Data
public class StudentGroups {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private long id;
  private String name;

}
