package ru.vstu.AuditorApi.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="students")
@NoArgsConstructor(force = true)
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    private int groupid;

    private String surname;

    private String name;

    private String moodlelogin;

}
