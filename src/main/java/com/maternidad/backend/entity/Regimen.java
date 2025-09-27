package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="Regimen")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Regimen {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="Nombre", nullable=false, unique=true, length=45)
    private String nombre;
}
