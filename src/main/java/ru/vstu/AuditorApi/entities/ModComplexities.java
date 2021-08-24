package ru.vstu.AuditorApi.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="mod_complexities")
@NoArgsConstructor(force = true)
@Data
public class ModComplexities {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private long id;

  private String name;

  private double kcmin;

  private double kcmax;

}
