package ru.vstu.AuditorApi.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="variants")
@NoArgsConstructor(force = true)
@Data
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private long lessontypeid;

    private long num;

    private String description;

    private String comments;
}
