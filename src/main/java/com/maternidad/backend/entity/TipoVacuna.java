package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="TipoVacuna")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TipoVacuna {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="Nombre", nullable=false, unique=true, length=200)
    private String nombre;
}