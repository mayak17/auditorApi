package ru.vstu.AuditorApi.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="lessons")
@NoArgsConstructor(force = true)
@Data
public class Lessons {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private long id;

  private long lessontypeid;
  private long groupid;
  private long teacherid;
  private long placeid;
  private java.sql.Date dateoflesson;
  private String nameoflesson;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getLessontypeid() {
    return lessontypeid;
  }

  public void setLessontypeid(long lessontypeid) {
    this.lessontypeid = lessontypeid;
  }


  public long getGroupid() {
    return groupid;
  }

  public void setGroupid(long groupid) {
    this.groupid = groupid;
  }


  public long getTeacherid() {
    return teacherid;
  }

  public void setTeacherid(long teacherid) {
    this.teacherid = teacherid;
  }


  public long getPlaceid() {
    return placeid;
  }

  public void setPlaceid(long placeid) {
    this.placeid = placeid;
  }


  public java.sql.Date getDateoflesson() {
    return dateoflesson;
  }

  public void setDateoflesson(java.sql.Date dateoflesson) {
    this.dateoflesson = dateoflesson;
  }


  public String getNameoflesson() {
    return nameoflesson;
  }

  public void setNameoflesson(String nameoflesson) {
    this.nameoflesson = nameoflesson;
  }

}
