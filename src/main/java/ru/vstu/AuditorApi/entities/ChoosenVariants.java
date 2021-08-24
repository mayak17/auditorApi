package ru.vstu.AuditorApi.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="choosen_variants")
@NoArgsConstructor(force = true)
@Data
public class ChoosenVariants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    private long variantid;

    private long studentid;

    private long active;

    private long lessonid;

}
