package ru.alexlemurski.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "t_author")
public final class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long id;

    @Column(name = "c_surname")
    private String surName;

    @Column(name = "c_name")
    private String name;

    @Column(name = "c_middle_name")
    private String middleName;

}