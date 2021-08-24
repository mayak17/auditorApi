package ru.vstu.AuditorApi.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="lesson_types")
@NoArgsConstructor(force = true)
@Data
public class LessonTypes {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private long id;

  private long num;
  private String name;
  private long usequiz;
  private long usesrs;
  private long usemod;
  private long usecodestyle;
  private long useprotocol;
  private long usealgorithtm;
  private long prerequizite1Id;
  private long prerequizite2Id;
  private long prerequizite3Id;
  private long andorprereq;
  private double quizRate;
  private double srsMinRate;
  private double srsAttPenalty;
  private long srsAttNormal;
  private double srsAddRate;
  private double srsFirstBonus;
  private double modMinRating;
  private double modAttPenalty;
  private long modAttNormal;
  private double modTestPenalty;
  private long modTestNormal;
  private double modAddRate;
  private double modFirstBonus;
  private double modTimelyBonus;
  private double codestyleMinRate;
  private double codestyleAddRate;
  private double algorithmAddRate;
  private double protocolMinRate;
  private double protocolAddRate;
  private long srsVariantUnique;

}
