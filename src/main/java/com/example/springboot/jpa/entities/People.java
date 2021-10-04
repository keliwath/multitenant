package com.example.springboot.jpa.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "data")
public class People {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String name;
}
