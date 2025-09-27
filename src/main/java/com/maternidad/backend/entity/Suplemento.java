package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="Suplemento")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Suplemento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="Nombre", nullable=false, unique=true, length=200)
    private String nombre;
}