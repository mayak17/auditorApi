package ru.vstu.AuditorApi.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="modifications")
@NoArgsConstructor(force = true)
@Data
public class Modifications {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private long id;

  private long variantid;
  private long num;
  private double kc;
  private String description;
  private String comments;

}
